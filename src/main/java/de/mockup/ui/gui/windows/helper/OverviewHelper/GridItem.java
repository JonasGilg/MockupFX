package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.awt.*;

/**
 * GridItem represents a basic item on the overview grid, which contains an ImageView for the thumbnail and a Text for the title.
 */
class GridItem extends BorderPane {
	private static final Dimension THUMBNAIL_SIZE = new Dimension(200, 200);

	private final javafx.scene.control.Label text;
	private final ImageView thumbnail;

	GridItem(String title) {
		this.text = new Label(title);
		text.setStyle("-fx-padding: 10,0,0,0");
		text.setVisible(true);

		thumbnail = new ImageView();
		//getStylesheets().add("stylesheets/overview.css");
		this.setBottom(text);
		setAlignment(text, Pos.BOTTOM_CENTER);
		this.setCenter(thumbnail);
		this.setVisible(true);

	}

	/**
	 * deselects the item visually
	 */
	public void deselect() {
		thumbnail.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");
	}

	/**
	 * selects the item visually. Is called from it's inherited classes
	 */
	void select() {
		WorkingSurface.get().getOverviewPane().deselectAll();
		thumbnail.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(128,0,0,0.8), 10, 0, 0, 0)");
	}

	/**
	 * sets the image shown
	 *
	 * @param image Image
	 */
	void setThumbnail(Image image) {
		thumbnail.setFitWidth(THUMBNAIL_SIZE.width);
		thumbnail.setPreserveRatio(true);
		thumbnail.setImage(image);
		thumbnail.setVisible(true);
	}


	/**
	 * sets the title of the item
	 *
	 * @param title
	 */
	public void setText(String title) {
		text.setText(title);
	}

}
