package de.mockup.system.util;

import de.mockup.system.binding.ContentFactory;
import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.TemplateModel;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.ui.content.ContentComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implements the ModelManager. Handles Models and Factories.
 */
public class ModelManagerImpl implements ModelManager {

	private Map<String, ContentFactory> componentRegistry = new HashMap<>();
	private Map<String, Class<?>> modelRegistry = new HashMap<>();

	public <X> void registerModel(String type, Class<X> clazz) {

		if (modelRegistry.containsKey(type)) {
			System.out.println("WARN: Override ComponentType: " + type);
		}
		modelRegistry.put(type, clazz);
	}


	public <X extends BaseComponentModel> X createModel(String type) throws SystemException {
		try {
			if (!modelRegistry.containsKey(type)) {
				System.out.println("ERROR: Component not registered: " + type);
				throw new SystemException(SystemErrorCodes.CANNOT_CREATE_MODEL);
			}
			@SuppressWarnings("unchecked")
			X result = (X) modelRegistry.get(type).newInstance();
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SystemException(SystemErrorCodes.CANNOT_CREATE_MODEL);
		}
	}

	public void registerComponent(String type, ContentFactory factory) {
		componentRegistry.put(type, factory);
	}

	/**
	 * Returns a Set of all registered Factories. A list of all components can be created!
	 *
	 * @return
	 */
	public Set<String> getComponentTypes() {
		return componentRegistry.keySet();
	}

	public <X extends ContentComponent, S extends BaseComponentModel> X createComponent(S model) throws SystemException {
		if (!componentRegistry.containsKey(model.getType())) {
			throw new SystemException(SystemErrorCodes.CANNOT_CREATE_COMPONENT);
		}
		ContentFactory<X, S> factory = componentRegistry.get(model.getType());

		return factory.createComponent(model);
	}

	/**
	 * Converts a MComponent to a Model
	 *
	 * @param component
	 * @param <X>
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	public <X extends ContentComponent, S extends BaseComponentModel> S convert(X component) throws SystemException {
		S model = createModel(component.getType());

		ContentFactory<X, S> factory = componentRegistry.get(component.getType());
		factory.writeToModel(component, model);

		return model;
	}

	/**
	 * Copies a MComponent with its model.
	 * <p>
	 * If the Model is a Container its a deepcopy.
	 *
	 * @param component
	 * @param <X>       ContentObject (SwingComponent)
	 * @param <S>       BaseComponentModel
	 * @return
	 * @throws SystemException
	 */
	public <X extends ContentComponent, S extends BaseComponentModel> X copy(X component) throws SystemException {
		S model = convert(component);
		return convert(model);
	}

	/**
	 * Copies a BaseComponentModel.
	 * <p>
	 * If the Model is a Container its a deepcopy.
	 */
	public <S extends BaseComponentModel> S copy(S model) throws SystemException {
		JSONObject config = model.toConfig();
		S copy = createModel(model.getType());
		copy.fromConfig(config);
		return copy;
	}

	/**
	 * Creates a template from a model.
	 *
	 * @param model
	 * @return
	 */
	public TemplateModel createTemplate(String templateName, BaseComponentModel model) throws SystemException {
		TemplateModel template = new TemplateModel();
		template.fromConfig(model.toConfig());
		template.setName(templateName);
		template.setId(null);
		removeIds(template);
		return template;
	}

	/**
	 * Remove all ids from the components. Used for Templates.
	 *
	 * @param model
	 */
	private void removeIds(ContainerModel model) {
		List<BaseComponentModel> items = model.getItems();
        if(items != null) {
            for (BaseComponentModel child : items) {
                child.setId(null);
                if (child instanceof ContainerModel) {
                    removeIds((ContainerModel) child);
                }
            }
        }
	}

	/**
	 * Creates a Model from a TemplateModel.
	 *
	 * @param template
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	public <S extends BaseComponentModel> S createModel(TemplateModel template) throws SystemException {
		S model = createModel(template.getType());
		model.fromConfig(template.toConfig());
		return model;
	}

	/**
	 * Converts a Model to a MComponent
	 *
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	public <X extends ContentComponent, S extends BaseComponentModel> X convert(S model) throws SystemException {
		X component = this.createComponent(model);

		ContentFactory<X, S> factory = componentRegistry.get(model.getType());
		factory.readFromModel(model, component);

		return component;
	}

	/**
	 * Apply values from a component to a model
	 *
	 * @param component
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @throws SystemException
	 */
	public <X extends ContentComponent, S extends BaseComponentModel> void applyToModel(X component, S model) throws SystemException {
		ContentFactory<X, S> factory = componentRegistry.get(model.getType());
		factory.writeToModel(component, model);
	}

	/**
	 * Apply values from a model to a component
	 *
	 * @param component
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @throws SystemException
	 */
	public <X extends ContentComponent, S extends BaseComponentModel> void applyToComponent(S model, X component) throws SystemException {
		ContentFactory<X, S> factory = componentRegistry.get(model.getType());
		factory.readFromModel(model, component);
	}
}
