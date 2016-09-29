package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.model.components.textfields.LabelModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingLabel;

/**
 * SwingComponentFactoryBase to bind SwingLabel and LabelModel.
 */
public class SwingLabelFactory extends SwingComponentFactoryBase<SwingLabel, LabelModel> {


	@Override
	public SwingLabel createComponent(LabelModel model) {
		return new SwingLabel(model);
	}

	@Override
	public void writeToModel(SwingLabel component, LabelModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(LabelModel model, SwingLabel component) {
		readPropertiesFromModel(model, component);
	}
}
