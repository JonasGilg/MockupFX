package de.mockup.ui.gui.windows;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

/**
 * This <code>SplitPane</code> can contain different kinds of <code>TitledBorderPanes</code> and manages drag and drop operations.
 */
class DynamicSplitPane extends SplitPane {

	private Parent parent;

	public DynamicSplitPane(Orientation orientation) {
		super();
		setOrientation(orientation);
	}

	public void add(int pos, Node node) {
		getItems().add(pos, node);
	}

	public void remove(Node n) {
		getItems().remove(n);
		if(getItems().size() < 1 && parent instanceof SplitPane) {
			((SplitPane) parent).getItems().remove(this);
			parent = null;
		}
	}

	public boolean isAttached() {
		return parent != null;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
