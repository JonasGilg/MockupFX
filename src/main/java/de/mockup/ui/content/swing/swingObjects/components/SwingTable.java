package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingTable extends SwingComponent<JTable> {

	public SwingTable(BaseComponentModel model) {
		super(new JTable(new Object[2][2], new String[]{"A", "B"}), model);
		//this.setBackground(Color.white);
		//this.setOpaque(true);
		//this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//this.setWheelScrollingEnabled(true);
	}


	public void deleteColumnContent(int col) {
		for (int i = 0; i < getContent().getRowCount(); i++) {
			getContent().setValueAt("", i, col);
		}
	}


    /*############################################## Getter/Setter####################################################*/

	public JTable getTable() {
		return getContent();
	}


	/*########################## ContentObject Methods#########################################################################*/
	@Override
	public void configureContextMenu() {
		Menu tableMenu = new Menu("table");
		MenuItem tableMenu1 = new MenuItem("ImportData");
		MenuItem tableMenu2 = new MenuItem("ResizeToFitTable");
		tableMenu1.setOnAction(e -> {
			//TODO call setTableData(Object[][] data) with the source data
		});
		// tableMenu2.setOnAction(e -> setMTableSize());
		tableMenu.getItems().add(tableMenu1);
		tableMenu.getItems().add(tableMenu2);
		this.getContextMenu().addMenuItemAt(tableMenu, 1);
	}

	@Override
	public String getDescription() {
		return "Table";
	}

	@Override
	public java.util.List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJTableProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJTablePropertyPanel(this);
	}

	@Override
	public String getType() {
		return ModelTypes.TABLE;
	}

	@Override
	public List<String> getStringKeys() {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < getContent().getModel().getColumnCount(); i++) {
			result.add(getIdentifier() + "_" + "columnName" + i);
		}
		return result;
	}
}