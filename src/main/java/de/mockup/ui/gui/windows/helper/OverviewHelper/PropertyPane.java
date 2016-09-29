package de.mockup.ui.gui.windows.helper.OverviewHelper;

import javafx.scene.layout.Pane;

/**
 * Pane that manages to show either ProjectPropertyPane or ViewPropertyPane.
 */
public class PropertyPane extends Pane {


	public PropertyPane(){
        this.getStylesheets().add("/stylesheets/overview.css");

	}

	/**
	 * shows the ProjectPropertyPane
	 * is called when the view overview is shown.
	 *
	 */
	public void loadStartPage(){
		this.getChildren().clear();
		this.getChildren().add(new ProjectPropertyPane());
		this.setVisible(true);
		this.getStylesheets().add("stylesheets/overview.css");
	}

	/**
	 * is called whenever a ViewItem is selected
	 * @param item selected ViewItem
	 */
	public void loadView(ViewItem item){

		this.getChildren().clear();
		this.getChildren().add(new ViewPropertyPane(item));


	}

}
