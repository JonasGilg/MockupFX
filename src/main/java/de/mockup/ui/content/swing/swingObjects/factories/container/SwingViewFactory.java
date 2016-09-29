package de.mockup.ui.content.swing.swingObjects.factories.container;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.ui.content.swing.swingObjects.SwingView;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

/**
 * Bind SwingView and ViewModel
 */
public class SwingViewFactory extends SwingComponentFactoryBase<SwingView, ViewModel> {

	@Override
	public SwingView createComponent(ViewModel model) {
		return new SwingView(model);
	}

	@Override
	public void writeToModel(SwingView component, ViewModel model) throws SystemException {
		writePropertiesToModel(component, model);
		addComponentsToModel(component, model);
		model.setTitle(component.getTitle());
	}

	@Override
	public void readFromModel(ViewModel model, SwingView component) throws SystemException {
		readPropertiesFromModel(model, component);
		addComponentsFromModel(model, component);
		component.setTitle(model.getTitle());
	}
}
