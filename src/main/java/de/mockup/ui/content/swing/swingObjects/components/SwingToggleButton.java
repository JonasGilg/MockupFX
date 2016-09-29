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

public class SwingToggleButton extends SwingComponent<JToggleButton> {

	public SwingToggleButton(BaseComponentModel model) {
		super(new JToggleButton(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Button.TOGGLE_BUTTON;
	}

	@Override
	public String getDescription() {
		return "ToggleButton";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJToggleButtonProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJToggleButtonPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys() {
		ArrayList<String> result = new ArrayList<>();
		result.add(getIdentifier() +"_" + "togglebuttontext");
		return result;
	}
}