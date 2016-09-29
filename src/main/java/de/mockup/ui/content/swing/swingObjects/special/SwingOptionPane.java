package de.mockup.ui.content.swing.swingObjects.special;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.container.SwingContainer;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingOptionPane extends SwingContainer<JOptionPane> {

	public SwingOptionPane(BaseComponentModel model) {
		super(new JOptionPane(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Container.OPTION_PANE;
	}

	@Override
	public String getDescription() {
		return "OptionPane";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addBasicProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(SwingPropertyPanelFactory.createJComponentPropertyPanel(this));
		return result;
	}
	
	@Override public List<String> getStringKeys(){
		return new ArrayList<>();
	}
}