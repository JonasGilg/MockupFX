package de.mockup.ui.content.swing.manager;

import de.mockup.ui.content.ContentView;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;

import javax.swing.*;

public class SwingViewController {

	public static void repaintView(Node view) {
		((SwingNode) view).getContent().repaint();
	}

	/**
	 * Creates a new instance of a <code>Node</code> to serve as a ViewNode and initializes it.
	 * @param view
	 * @return a <code>SwingNode</code>
	 */
	public static Node getViewNode(ContentView<JPanel> view) {
		SwingNode node = new SnapshotableSwingNode(view);

		JPanel root = new JPanel();
		root.setLayout(null);
		root.add(view.getContent());

		node.setContent(root);

		repaintView(node);

		return node;
	}
}
