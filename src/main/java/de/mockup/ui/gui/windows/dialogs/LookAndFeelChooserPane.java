package de.mockup.ui.gui.windows.dialogs;


import de.mockup.annotations.NeedsLayerSeperation;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.content.swing.manager.SwingLaFManager;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * Has a {@link TreeView} on the left, where the user can select a look and feel and has a preview on the right.
 */
class LookAndFeelChooserPane extends BorderPane {

	private final String curLaF;
	private final String curTheme;

	public LookAndFeelChooserPane() {
        curLaF = GuiToContentFacade.LaF.getLaF();
        curTheme = GuiToContentFacade.LaF.getTheme();

		setCenter(GuiToContentFacade.LaF.getPreview());
		createTree();
    }

	/**
     * Creates the {@link TreeView} that contains all look and feels and themes.
     */
    @NeedsLayerSeperation
	private void createTree() {
		TreeView<String> list = new TreeView<>();
		TreeItem<String> root = new TreeItem<>();
		list.setRoot(root);
		list.setShowRoot(false);

		for (String laf : SwingLaFManager.getKeys()) {
			TreeItem<String> lafRoot = new TreeItem<>(laf);
			root.getChildren().add(lafRoot);
			for (String theme : SwingLaFManager.getThemesToLaF(laf)) {
				TreeItem<String> themeLeaf = new TreeItem<>(theme);
				lafRoot.getChildren().add(themeLeaf);
				if (GuiToContentFacade.LaF.getLaF().equals(laf) && GuiToContentFacade.LaF.getTheme().equals(theme)) {
					list.getSelectionModel().select(themeLeaf);
				}
			}
		}

		list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isLeaf()) {
				GuiToContentFacade.LaF.setCurrentLaF(newValue.getParent().getValue(), newValue.getValue());
				setCenter(GuiToContentFacade.LaF.getPreview());
			}
		});
		setLeft(list);
	}

	/**
     * Applies the currently selected look and feel to the whole project.
     */
    public void apply() {
		GuiToContentFacade.LaF.updateUI();
		try {
			WorkingSurface.get().getSelectedTab().repaint();
		} catch (Exception ignored) {
		}
	}

	/**
     * Cancels the selection of the look and feel.
     */
    public void cancel() {
		GuiToContentFacade.LaF.setCurrentLaF(curLaF, curTheme);
	}

}
