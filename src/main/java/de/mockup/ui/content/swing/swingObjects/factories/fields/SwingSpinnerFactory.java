package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.SpinnerModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingSpinner;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

public class SwingSpinnerFactory extends SwingComponentFactoryBase<SwingSpinner, SpinnerModel> {

	@Override
	public SwingSpinner createComponent(SpinnerModel model) {
		return new SwingSpinner(model);
	}

	@Override
	public void writeToModel(SwingSpinner component, SpinnerModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(SpinnerModel model, SwingSpinner component) {
		readPropertiesFromModel(model, component);
	}

}
