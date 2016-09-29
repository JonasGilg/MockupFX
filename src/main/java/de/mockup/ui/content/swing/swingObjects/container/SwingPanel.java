package de.mockup.ui.content.swing.swingObjects.container;

import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class SwingPanel extends SwingContainer<JPanel> {

	public SwingPanel(BaseComponentModel model) {
		super(new JPanel(), model);
		getContent().setBorder(BorderFactory.createEtchedBorder());
		getContent().setOpaque(true);
		getContent().setLayout(null);
		getContent().setFocusable(true);
	}

	@Override
	public String getDescription() {
		return "Panel";
	}

	/**
	 * returns a list of modifiable properties of this component.
	 * -Background color
	 * -Opaque
	 * TODO more properties
	 */
	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJPanelProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJPanelPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys(){
		return new ArrayList<>();
	}
}
