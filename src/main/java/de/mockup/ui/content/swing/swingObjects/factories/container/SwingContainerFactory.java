package de.mockup.ui.content.swing.swingObjects.factories.container;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.swing.swingObjects.container.SwingPanel;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

import javax.swing.*;

/**
 * ComponentFactory to bind SwingPanel and ContainerModel
 */
public class SwingContainerFactory extends SwingComponentFactoryBase<ContentContainer<? extends JComponent>, ContainerModel> {

	@Override
	public SwingPanel createComponent(ContainerModel model) {
		return new SwingPanel(model);
	}

	@Override
	public void writeToModel(ContentContainer<? extends JComponent> component, ContainerModel model) throws SystemException {
		//Write Properties
		writePropertiesToModel(component, model);

		addComponentsToModel(component, model);
	}

	@Override
	public void readFromModel(ContainerModel model, ContentContainer<? extends JComponent> component) throws SystemException {
		//Read Properties
		readPropertiesFromModel(model, component);
		addComponentsFromModel(model, component);
	}

}
