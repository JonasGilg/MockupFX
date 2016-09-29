package de.mockup.ui.content.swing.swingObjects.container;

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

public class SwingTabbedPane extends SwingComponent<JTabbedPane> {


	public SwingTabbedPane(BaseComponentModel model) {
		super(new JTabbedPane(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Container.TABBED_PANE;
	}

	@Override
	public String getDescription() {
		return "TabbedPane";
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
	
	@Override public List<String> getStringKeys() {
		ArrayList<String> result = new ArrayList<>();
		for(int i=0; i < getContent().getTabCount(); i++){
		     result.add(getIdentifier() + "_" + "tabtitle" + i);
		     result.add(getIdentifier() + "_" + "tabtooltiptext" + i);
		}
		return result;
	}
	
}