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

public class SwingTextField extends SwingComponent<JTextField> {


	public SwingTextField(BaseComponentModel model) {
		super(new JTextField("your text here"), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.TEXT_FIELD;
	}


	@Override
	public String getDescription() {
		return "TextField";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addBasicProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJTextFieldPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys() {
		return new ArrayList<>();
	}
}