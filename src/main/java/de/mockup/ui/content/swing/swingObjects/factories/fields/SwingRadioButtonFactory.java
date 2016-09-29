package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.buttons.RadioButtonModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingRadioButton;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

public class SwingRadioButtonFactory extends SwingComponentFactoryBase<SwingRadioButton, RadioButtonModel> {

	@Override
	public SwingRadioButton createComponent(RadioButtonModel model) {
		return new SwingRadioButton(model);
	}

	@Override
	public void writeToModel(SwingRadioButton component, RadioButtonModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(RadioButtonModel model, SwingRadioButton component) {
		readPropertiesFromModel(model, component);
	}

}
