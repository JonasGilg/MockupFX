package de.mockup.system.util;

import de.mockup.system.Bundle;
import de.mockup.system.binding.ContentFactory;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.TemplateModel;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.ContentComponent;

import java.util.Set;

/**
 * Handles the ModelBindings
 */
public interface ModelManager {


	//Legacy Code. ModelManager is handled by the Bundle
	static ModelManager get() {
		return Bundle.getService(ModelManager.class);
	}

	<X> void registerModel(String type, Class<X> clazz);

	<X extends BaseComponentModel> X createModel(String type) throws SystemException;

	void registerComponent(String type, ContentFactory factory);

	/**
	 * Returns a Set of all registered Factories. A list of all components can be created!
	 *
	 * @return
	 */
	Set<String> getComponentTypes();

	<X extends ContentComponent, S extends BaseComponentModel> X createComponent(S model) throws SystemException;

	/**
	 * Converts a MComponent to a Model
	 *
	 * @param component
	 * @param <X>
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	<X extends ContentComponent, S extends BaseComponentModel> S convert(X component) throws SystemException;

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
	<X extends ContentComponent, S extends BaseComponentModel> X copy(X component) throws SystemException;

	/**
	 * Copies a BaseComponentModel.
	 * <p>
	 * If the Model is a Container its a deepcopy.
	 */
	<S extends BaseComponentModel> S copy(S model) throws SystemException;

	/**
	 * Creates a template from a model.
	 *
	 * @param model
	 * @return
	 */
	TemplateModel createTemplate(String templateName, BaseComponentModel model) throws SystemException;

	/**
	 * Creates a Model from a TemplateModel.
	 * <b>Note: The generated Models dont have IDS!</b>
	 *
	 * @param template
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	<S extends BaseComponentModel> S createModel(TemplateModel template) throws SystemException;

	/**
	 * Converts a Model to a MComponent
	 *
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @return
	 * @throws SystemException
	 */
	<X extends ContentComponent, S extends BaseComponentModel> X convert(S model) throws SystemException;

	/**
	 * Apply values from a component to a model
	 *
	 * @param component
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @throws SystemException
	 */
	<X extends ContentComponent, S extends BaseComponentModel> void applyToModel(X component, S model) throws SystemException;

	/**
	 * Apply values from a model to a component
	 *
	 * @param component
	 * @param model
	 * @param <X>
	 * @param <S>
	 * @throws SystemException
	 */
	<X extends ContentComponent, S extends BaseComponentModel> void applyToComponent(S model, X component) throws SystemException;

}
