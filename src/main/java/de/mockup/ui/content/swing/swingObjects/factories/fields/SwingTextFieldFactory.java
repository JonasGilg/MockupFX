package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.textfields.TextFieldModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingTextField;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

public class SwingTextFieldFactory extends SwingComponentFactoryBase<SwingTextField, TextFieldModel> {

	@Override
	public SwingTextField createComponent(TextFieldModel model) {
		return new SwingTextField(model);
	}

	@Override
	public void writeToModel(SwingTextField component, TextFieldModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(TextFieldModel model, SwingTextField component) {
		readPropertiesFromModel(model, component);
	}

}
