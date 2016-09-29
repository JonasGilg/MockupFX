package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingSlider extends SwingComponent<JSlider> {


	public SwingSlider(BaseComponentModel model) {
		super(new JSlider(), model);
	}

	@Override
	public String getDescription() {
		return "Slider";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJSliderProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJSliderPropertyPanel(this);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.SLIDER;
	}
	
	@Override public List<String> getStringKeys(){
		return new ArrayList<>();
	}
}
