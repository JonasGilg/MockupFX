package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingEditorPane extends SwingComponent<JEditorPane> {


	public SwingEditorPane(BaseComponentModel model) {
		super(new JEditorPane(), model);
	}

	@Override
	public String getType() {
		//TODO:
		return null;
	}


	@Override
	public String getDescription() {
		return "EditorPane";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJEditorPaneProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJEditorPanePropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys() {
		return new ArrayList<>();
	}
}