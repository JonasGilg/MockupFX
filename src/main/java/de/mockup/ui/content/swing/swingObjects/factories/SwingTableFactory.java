package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.model.components.TableModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingTable;

public class SwingTableFactory extends SwingComponentFactoryBase<SwingTable, TableModel> {

	@Override
	public SwingTable createComponent(TableModel model) {
		return new SwingTable(model);
	}

	@Override
	public void writeToModel(SwingTable component, TableModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(TableModel model, SwingTable component) {
		readPropertiesFromModel(model, component);
	}

}
