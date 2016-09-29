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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class SwingTree extends SwingComponent<JTree> {

	private MenuItem itemRoot;

	public SwingTree(BaseComponentModel model) {
		super(new JTree(new DefaultMutableTreeNode("Root")), model);
		DefaultMutableTreeNode leaf1 = new DefaultMutableTreeNode("Hello");
		DefaultMutableTreeNode leaf2 = new DefaultMutableTreeNode("World");
		((DefaultMutableTreeNode) getContent().getModel().getRoot()).add(leaf1);
		((DefaultMutableTreeNode) getContent().getModel().getRoot()).add(leaf2);
		leaf2.add(new DefaultMutableTreeNode("grrrr"));
		getContent().expandRow(0);
		getContent().setOpaque(true);
		//TODO buggy this.addMouseListener(new SwingTreeListener(this));
	}

	//TODO altes KontextMenu richtig umschreiben
	@Override
	public void configureContextMenu() {
		itemRoot = new MenuItem("Wurzel hinzuf\u00fcgen");

		itemRoot.setOnAction(e -> {
			String result = JOptionPane.showInputDialog("Name of the new Root: ");
			if (result != null) {
				DefaultTreeModel model = (DefaultTreeModel) getContent().getModel();
				model.setRoot(new DefaultMutableTreeNode(result));
				setRootButton(false);
			}
		});

		setRootButton(false);
		//this.getContextMenu().addMenuItemAt(itemRoot,1);
	}

	public void setRootButton(boolean onOff) {
		itemRoot.setDisable(!onOff);
	}

	@Override
	public String getDescription() {
		return "Tree";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJTreeProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJTreePropertyPanel(this);
	}

	@Override
	public String getType() {
		return ModelTypes.TREE;
	}
	
	@Override public List<String> getStringKeys(){
		List<String> result = new ArrayList<>();
		Enumeration<?> enumer = ((DefaultMutableTreeNode)getContent().getModel().getRoot()).preorderEnumeration();
		int counter = 0;
	    while (enumer.hasMoreElements()) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumer.nextElement();
	        
	        while (node.getParent() != null) {
	           result.add(getIdentifier() + "_" + "treenode" + counter);
	           node = (DefaultMutableTreeNode) node.getParent();
	           counter++;
	        }
	    }
		return result;
	}
	
	
}