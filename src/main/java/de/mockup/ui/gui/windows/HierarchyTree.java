package de.mockup.ui.gui.windows;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.guiController.SelectionManager;
import de.mockup.ui.gui.guiController.WindowManager;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Observable;
import java.util.Observer;

/**
 * This Window displays the elements on the WorkingSurface in a hierarchy(drilldown menu)
 *
 * @author Janos Vollmer
 */
public class HierarchyTree extends TitlePane implements Observer {

	/**
	 * Hierarchy Tree
	 */
	private TreeView<ComponentTreeModel> hierarchy;

	public HierarchyTree(String name) { super(name); }

	/**
	 * Convenience method to get the Instance of this window
	 * @return instance of this window
	 */
	public static synchronized HierarchyTree get() {
		return WindowManager.get().getWindow(HierarchyTree.class);
	}

	@Override
	public void update(Observable o, Object arg) {
		ContentComponent observing = (ContentComponent) o;
		if(observing.getParent() == null) {
			remove(observing);
		} else {
			changeParent(observing, observing.getParent());
		}
        setLabel(observing);
	}

	private enum iteratorMode {
		Remove, Search
	}

	private class ComponentTreeModel {
		private final ContentComponent component;
		private String label;
		private Integer parentId;

		public ComponentTreeModel(String label, ContentComponent component) {
			this.component = component;
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Integer getId() {
			if (component != null) {
				return component.getComponentModel().getId();
			}
			return null;
		}

		public Integer getParentId() {
			return parentId;
		}

		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}

		public ContentComponent getComponent() {
			return component;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	/**
	 * Creates a Tree with parameter name as root. Parameter should be project name.
	 *
	 * @param name Name of root
	 */
	public void initTree(String name) {
		hierarchy = null;
		TreeItem<ComponentTreeModel> rootItem = new TreeItem<>(new ComponentTreeModel(name, null));
		rootItem.setExpanded(true);
		hierarchy = new TreeView<>(rootItem);
		setContent(hierarchy);
		getScene().getStylesheets().add("stylesheets/tree.css");
		hierarchy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.equals(hierarchy.getRoot())) {
				ContentComponent item = newValue.getValue().getComponent();
				SelectionManager.setSingleSelection(item);
				while(!(item instanceof ContentView)) {
					item = item.getParent();
				}

				WorkingSurface.get().selectView((ContentView)item);
				newValue.setExpanded(true);
				WorkingSurface.get().repaintOverlay();
			}
		});
	}

	/**
	 * Adds new node to the tree.
	 *
	 * @param component Added component
	 */
	public void add(ContentComponent component) {
		TreeItem<ComponentTreeModel> treeItem = createTreeItem(component);
		//Sets the id of the parent to the treemodel
		treeItem.getValue().setParentId(component.getParent().getComponentModel().getId());
		addChild(hierarchy.getRoot(), treeItem);
	}
	
	/**
	 * Adds new node to the tree.
	 * @param parent Parent of new component.
	 * @param child Component to add.
	 * @return
	 */
	private boolean addChild(TreeItem<ComponentTreeModel> parent, TreeItem<ComponentTreeModel> child) {
		for (TreeItem<ComponentTreeModel> node : parent.getChildren()) {
			if (node.getValue().getId().equals(child.getValue().getParentId())) {
				node.getChildren().add(child);
				node.setExpanded(true);
				return true;
			} else if (node.getChildren().size() > 0) {
				boolean added = addChild(node, child);
				if (added) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Creates a new TreeItem from the model.
	 * @param component Component to be added.
	 * @return Returns a new TreeItem.
	 */
	private TreeItem<ComponentTreeModel> createTreeItem(ContentComponent component) {
		//TODO Create resources for bind a label on a type
		return new TreeItem<>(new ComponentTreeModel(component.getDescription(), component));
	}

	/**
	 * Removes a Node from the tree.
	 *
	 * @param c Removed ContentComponent
	 */
	public void remove(ContentComponent c) {
		int id = c.getComponentModel().getId();
		iterator(hierarchy.getRoot(), id, iteratorMode.Remove);
	}

	/**
	 * Removes a view form the tree.
	 *
	 * @param view Removed view
	 */
	public void removeView(ContentView view) {
		int id = view.getComponentModel().getId();
		iterator(hierarchy.getRoot(), id, iteratorMode.Remove);
	}

	/**
	 * Adding new View to the tree.
	 *
	 * @param view View to add
	 */
	public void addView(ContentView view) {
		TreeItem<ComponentTreeModel> viewItem = createTreeItem(view);
		if(view.getChildren() != null && view.getChildren().length > 0) {
			addTreeChilds(viewItem, view);
		}
		hierarchy.getRoot().getChildren().add(viewItem);
	}
	
	/**
	 * Adds multiple nodes to the tree.
	 */
	private void addTreeChilds(TreeItem<ComponentTreeModel> parent, ContentContainer component){
		for(ContentComponent c : component.getChildren()){
			TreeItem<ComponentTreeModel> child = createTreeItem(c);
			parent.getChildren().add(child);
			if(c instanceof ContentContainer){
				addTreeChilds(child, (ContentContainer) c);
			}
		}
	}

	/**
	 * Select a given component in the Tree
	 * @param c Component to select
	 */
	public void select(ContentComponent c) {
		if(c != null) {
			int id = c.getComponentModel().getId();
			TreeItem<ComponentTreeModel> item = iterator(hierarchy.getRoot(), id, iteratorMode.Search);
			hierarchy.getSelectionModel().select(item);
		}
	}

	/**
	 * Clears all selections.
	 */
	public void deselect() {
		hierarchy.getSelectionModel().clearSelection();
	}
	
	/**
	 * Iterating through the tree to find a node by component-id.
	 * @param root TreeRoot
	 * @param id Unique ID of the component you are searching for
	 * @param mode Find/Delete
	 * @return Returns the found/deleted component
	 */
	private TreeItem<ComponentTreeModel> iterator(TreeItem<ComponentTreeModel> root, int id, iteratorMode mode) {
		TreeItem<ComponentTreeModel> result;
		if (!root.isLeaf()) {
			for (TreeItem<ComponentTreeModel> node : root.getChildren()) {
				result = iterator(node, id, mode);
				if (result != null) {
					if (mode == iteratorMode.Remove) {
						root.getChildren().remove(result);
					}
					return result;
				}
			}
		}
		if (root.getValue().getId() != null && root.getValue().getId() == id) {
			return root;
		} else {
			return null;
		}

	}

	/**
	 * Changes the parent of a component. Called, when dragging a component into a new container.
	 * @param c Dragged Component
	 * @param newParent Drop Target
	 */
	private void changeParent(ContentComponent c, ContentContainer newParent) {
		TreeItem<ComponentTreeModel> node = iterator(hierarchy.getRoot(), c.getComponentModel().getId(),
				iteratorMode.Search);
		if(node != null && node.getParent().getValue().getComponent() != newParent) {
			node.getParent().getChildren().remove(node);
			iterator(hierarchy.getRoot(), newParent.getComponentModel().getId(),
					iteratorMode.Search).getChildren().add(node);
			select(c);
		}
	}

	/**
	 * Changes the label of an item in the HierarchyTree
	 *
	 * @param component The component who's name should be changed
	 *
	 */
	private void setLabel(ContentComponent component) {
		TreeItem<ComponentTreeModel> node = iterator(hierarchy.getRoot(), component.getComponentModel().getId(),
				iteratorMode.Search);
		if(node != null) {
			node.getValue().setLabel(component.getDescription());
            hierarchy.refresh();
		}
	}
	
	/**
	 * Changes name of the Root. Needed when renaming a project.
	 * @param name New Name
	 */
	public void renameRoot(String name) {
        hierarchy.getRoot().getValue().setLabel(name);
        hierarchy.refresh();
	}
}
