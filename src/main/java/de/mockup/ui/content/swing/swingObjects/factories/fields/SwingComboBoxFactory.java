package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.ComboBoxModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingComboBox;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

public class SwingComboBoxFactory<E> extends SwingComponentFactoryBase<SwingComboBox<E>, ComboBoxModel> {

	@Override
	public SwingComboBox createComponent(ComboBoxModel model) {
		return new SwingComboBox(model);
	}

	@Override
	public void writeToModel(SwingComboBox component, ComboBoxModel model) {
		writePropertiesToModel(component, model);		
	}

	@Override
	public void readFromModel(ComboBoxModel model, SwingComboBox component) {
        readPropertiesFromModel(model, component);		
	}

}
