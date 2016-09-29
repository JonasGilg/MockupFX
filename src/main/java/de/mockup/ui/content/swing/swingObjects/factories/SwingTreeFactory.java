package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.model.components.TreeModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingTree;

public class SwingTreeFactory extends SwingComponentFactoryBase<SwingTree, TreeModel> {

	@Override
	public SwingTree createComponent(TreeModel model) {
		return new SwingTree(model);
	}

	@Override
	public void writeToModel(SwingTree component, TreeModel model) {
		writePropertiesToModel(component, model);		
	}

	@Override
	public void readFromModel(TreeModel model, SwingTree component) {
        readPropertiesFromModel(model, component);		
	}

}
