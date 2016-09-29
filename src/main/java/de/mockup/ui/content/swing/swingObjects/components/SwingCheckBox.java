package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import javafx.scene.control.MenuItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingCheckBox extends SwingComponent<JCheckBox> {

	public SwingCheckBox(BaseComponentModel model) {
		super(new JCheckBox("CheckBox"), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.CHECKBOX;
	}

	@Override
	public void configureContextMenu() {
		/*-------------------non-default MenuItems-------------------*/
		MenuItem itemRename = new MenuItem("Rename");


		/*-------------------add Listener to non-default Items-------*/
		itemRename.setOnAction(e -> {
			String result = JOptionPane.showInputDialog("new Text?");
			if (result != null) {
				getContent().setText(result);
			}
		});
		/*-------------------add Item to contextMenu------------------*/
		this.getContextMenu().addMenuItemAt(itemRename, 1);

	}

	@Override
	public String getDescription() {
		return "Checkbox";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJCheckBoxProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJCheckBoxPropertyPanel(this);
	}

	@Override public List<String> getStringKeys(){
		List<String> result = new ArrayList<>();
		result.add(getIdentifier() +"_" + "checkboxtext");
		return result;
	}
}
