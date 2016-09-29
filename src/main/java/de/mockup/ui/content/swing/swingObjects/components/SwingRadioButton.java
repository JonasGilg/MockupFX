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

public class SwingRadioButton extends SwingComponent<JRadioButton> {

	public SwingRadioButton(BaseComponentModel model) {
		super(new JRadioButton("RadioButton"), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.RADIO;
	}

	@Override
	public String getDescription() {
		return "RadioButton";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJRadioButtonProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJRadioButtonPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys(){
		List<String> result = new ArrayList<>();
		result.add(getIdentifier() +"_" + "radiobuttontext");
		return result;
	}
}
