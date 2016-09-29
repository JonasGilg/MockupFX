package de.mockup.ui.content.swing.swingObjects.menu;

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

public class SwingPopupMenu extends SwingComponent<JPopupMenu> {

	public SwingPopupMenu(BaseComponentModel model) {
		super(new JPopupMenu(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.POPUP_MENU;
	}

	@Override
	public String getDescription() {
		return "PopUpMenu";
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
		List<String> result = new ArrayList<>();
		result.add(getIdentifier() + "_" + "menulabeltext");
		return result;
	}
}