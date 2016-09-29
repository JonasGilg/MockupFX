package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.ui.gui.guiController.OverviewController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Button which only has one function: Switch between the TabPane and the OverviewPane on the WorkingSurface and PropertyWindow
 */
public class OverviewButton extends Button {
	public OverviewButton(){
		ImageView imageView = new ImageView("icons/view-overview.png");
		imageView.setFitHeight(10);
		imageView.setFitWidth(20);
		setGraphic(imageView);
		this.setVisible(false);
		//getStylesheets().add("stylesheets/overview.css");

		this.setOnAction(event -> 	OverviewController.toggleOverview());
		getStylesheets().add("stylesheets/overview.css");
		this.setPrefSize(10,10);

	}
}
