package de.mockup.ui;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.content.swing.manager.SwingController;
import de.mockup.ui.content.swing.manager.SwingLaFManager;
import de.mockup.ui.content.swing.manager.SwingViewController;
import de.mockup.ui.gui.guiController.ProjectController;
import javafx.scene.Node;

import java.awt.*;

/**
 * This is the Facade class, that enables encapsulation of the GUI from its displayed content. This enables to swap the
 * content without the GUI noticing.
 */
public class GuiToContentFacade {

	/**
	 * A utility method, which is called when a new Project is being created or an old Project is being loaded.
	 */
	public static void initContent() {
		switch (ProjectController.PROJECT_TYPE) {
			case SWING:
				SwingController.init();
		}
	}

	/**
	 * @param view
	 * @return a node, which contains the view content corresponding to the current project type.
	 */
	public static Node getViewNode(ContentView view) {
		switch (ProjectController.PROJECT_TYPE) {
			case SWING:
				return SwingViewController.getViewNode(view);
            case JAVAFX:
                return null;
            case BROWSER:
                return null;
		}
		return null;
	}

	/**
	 * Refreshes the content of the specified view.
	 * @param view the view to refresh.
	 */
	public static void updateViewGraphics(Node view) {
		switch (ProjectController.PROJECT_TYPE) {
			case SWING:
				SwingViewController.repaintView(view);
		}
	}

	/**
	 * Tells the content to emplace the child into the parent at the specified point.
	 * @param child
	 * @param parent
	 * @param p
	 */
	public static void addToParent(ContentComponent child, ContentContainer parent, Point p) {
		switch (ProjectController.PROJECT_TYPE) {
			case SWING:
				SwingController.addToParent(child, parent, p);
				break;
		}
	}

	/**
	 * Contains Look and Feel related facade methods.
	 */
	public static class LaF {

		/**
		 * Provides a preview node for the contents Look and Feel.
		 * @return a node which contains the preview
		 */
		public static Node getPreview() {
			switch (ProjectController.PROJECT_TYPE) {
				case SWING:
					return SwingLaFManager.getPreview();
				case JAVAFX:
					return null;
				case BROWSER:
					return null;
			}
			return null;
		}

		/**
		 * Refreshes the UI to apply changes to the Look and feel.
		 */
		public static void updateUI() {
			switch (ProjectController.PROJECT_TYPE) {
				case SWING:
					SwingLaFManager.updateUI();
					break;
				case JAVAFX:
					break;
				case BROWSER:
					break;
			}
		}

		/**
		 * Returns the current Look and Feel name of the content.
		 * @return the Look and Feel name
		 */
		public static String getLaF() {
			switch (ProjectController.PROJECT_TYPE) {
				case SWING:
					return SwingLaFManager.getCurrentLaF();
			}
			return null;
		}

		/**
		 * Returns the current theme of the Look and Feel.
		 * @return the name of the theme
		 */
		public static String getTheme() {
			switch (ProjectController.PROJECT_TYPE) {
				case SWING:
					return SwingLaFManager.getCurrentTheme();
			}
			return null;
		}

		/**
		 * Sets the Look and Feel of the content.
		 * @param laf the name of the Look and Feel
		 * @param theme the name of the theme
		 */
		public static void setCurrentLaF(String laf, String theme) {
			switch (ProjectController.PROJECT_TYPE) {
				case SWING:
					SwingLaFManager.setCurrentLaF(laf, theme);
					break;
			}
		}
	}

	/**
	 * Removes a component from the contents scene graph.
	 * @param c the component to remove
	 */
    @Deprecated //TODO de.mockup.ui.gui.guiController.ProjectController.deleteComponent should be used instead
	public static void deleteComponent(ContentComponent c) {
		switch (ProjectController.PROJECT_TYPE) {
			case SWING:
				SwingController.deleteComponent(c);
				break;
		}
	}
}
