package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.SliderModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingSlider;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

public class SwingSliderFactory extends SwingComponentFactoryBase<SwingSlider, SliderModel> {

	@Override
	public SwingSlider createComponent(SliderModel model) {
		return new SwingSlider(model);
	}

	@Override
	public void writeToModel(SwingSlider component, SliderModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(SliderModel model, SwingSlider component) {
		readPropertiesFromModel(model, component);
	}

}
