package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.binding.ContentFactory;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.ChildModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * BaseClass for ComponentFactories.
 * <p>
 * Contains methods to read and write Properties.
 */
public abstract class SwingComponentFactoryBase<T extends ContentComponent<? extends JComponent>, S extends BaseComponentModel> extends ContentFactory<T, S> {

	protected void writePropertiesToModel(ContentComponent<? extends JComponent> component, BaseComponentModel model) {
		//Write Properties to Model
		List<Property<?>> properties = component.getProperties();
		for (Property<?> p : properties) {
			model.setProperty(p.getVarName(), p.toConfig());
		}
	}

	protected void readPropertiesFromModel(BaseComponentModel model, ContentComponent<? extends JComponent> component) {
		List<Property<?>> compProperties = component.getProperties();
		Map<String, Object> storedProperties = model.getPropertyMap();
		for (Property<?> p : compProperties) {
			String key = p.getVarName();
			if (storedProperties.containsKey(key)) {
				Object value = storedProperties.get(key);
				p.fromConfig(value);
			}
		}
	}

	protected <X extends ContainerModel> void addComponentsToModel(ContentComponent<? extends JComponent> component, X model) throws SystemException {
		//Write Components to Model
		//TODO: can be done better?
		if(component instanceof ContentContainer) {
			ContentComponent[] components = ((ContentContainer) component).getChildren();
			for (ContentComponent c : components) {
				ChildModel child = (ChildModel) ModelManager.get().convert(c);
				model.addItem(child);
			}
		}
	}

	protected <X extends ContainerModel> void addComponentsFromModel(X model, ContentComponent<? extends JComponent> component) throws SystemException {
		//Read Components
		//TODO: can be done better?
		if(component instanceof ContentContainer) {
			ModelManager manager = ModelManager.get();
			if (model.getItems() != null) {
				for (BaseComponentModel m : model.getItems()) {
					((ContentContainer) component).addChild(manager.convert(m));
				}
			}
		}
	}
}
