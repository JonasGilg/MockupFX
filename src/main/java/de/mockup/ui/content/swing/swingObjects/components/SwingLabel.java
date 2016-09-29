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

public class SwingLabel extends SwingComponent<JLabel> {

	public SwingLabel(BaseComponentModel model) {
		super(new JLabel(RESOURCE_BUNDLE.getString("new.label")), model);
		getContent().setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public String getDescription() {
		return getContent().getText();
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addBasicProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJLabelPropertyPanel(this);
	}

	@Override
	public String getType() {
		return ModelTypes.LABEL;
	}
	
	@Override public List<String> getStringKeys() {
		ArrayList<String> result = new ArrayList<>();
		result.add(getIdentifier() + "_" + "labeltext");
		return result;
	}
}