package de.mockup.ui.gui.guiController;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.windows.HierarchyTree;
import de.mockup.ui.gui.windows.MainFrame;
import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.WorkingSurface;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the selections of Components.
 */
public class SelectionManager {
	private static ContentComponent singleSelection;
	private static ArrayList<ContentComponent> multiSelection;
	private static Rectangle selection = new Rectangle();

	private static boolean marking;


	/**
	 * Sets a single Component as the selected Component.
	 */
	public static void setSingleSelection(ContentComponent m) {
		multiSelection = null;
		singleSelection = m;
		MainFrame.get().setCopyButtonState(true);
		MainFrame.get().setCutButtonState(true);
		WorkingSurface.get().repaintOverlay();

		PropertyWindow.setComponentOfInterest(m);
		HierarchyTree.get().select(m);
	}

	/**
	 * Sets multiple Components as selected.
	 */
	public static void setMultiSelection(ArrayList<ContentComponent> m, Rectangle selection) {
		singleSelection = null;
		multiSelection = m;
		SelectionManager.selection = selection;

		MainFrame.get().setCopyButtonState(true);
		MainFrame.get().setCutButtonState(true);

		WorkingSurface.get().repaintOverlay();
	}

	public static ContentComponent getSingleSelection() {
		return singleSelection;
	}

	public static ArrayList<ContentComponent> getMultiSelection() {
		return multiSelection;
	}

	public static Rectangle getSelection() {
		return selection;
	}

	public static boolean isMarking() {
		return marking;
	}

	public static void setMarking(boolean marking) {
		SelectionManager.marking = marking;
	}

	public static boolean isSelection() {
		return (getMultiSelection() != null) | (getSingleSelection() != null);
	}

	public static ArrayList<ContentComponent> getSelectedItems() {
		ArrayList<ContentComponent> items = new ArrayList<>();
		if (SelectionManager.getMultiSelection() != null || SelectionManager.getSingleSelection() != null) {

			if (SelectionManager.getMultiSelection() != null) {
				items = getMultiSelection();
			} else {
				items.add(getSingleSelection());

			}
		}
		return items;
	}

	public static void deselect() {
		singleSelection = null;
		multiSelection = null;
		PropertyWindow.setComponentOfInterest(null);
		WorkingSurface.get().repaintOverlay();
		HierarchyTree.get().deselect();
	}
}
