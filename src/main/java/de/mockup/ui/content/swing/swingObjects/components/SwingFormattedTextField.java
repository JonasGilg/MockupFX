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

public class SwingFormattedTextField extends SwingComponent<JFormattedTextField> {


	public SwingFormattedTextField(BaseComponentModel model) {
		super(new JFormattedTextField(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.FORMATTED_TEXTFIELD;
	}

	@Override
	public String getDescription() {
		return "FormattedTextField";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJFormattedTextFieldProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJFormattedTextFieldPropertyPanel(this);
	}
	@Override public List<String> getStringKeys() {
		return new ArrayList<>();
	}
}