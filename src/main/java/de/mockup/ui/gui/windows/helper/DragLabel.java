package de.mockup.ui.gui.windows.helper;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Used for drag/drop of components.
 */
public class DragLabel extends Label {
	private final String type;

	public DragLabel(String title, String type) {
		super(title);
		this.type = type;

		setOnDragDetected(event -> {
			Dragboard db = this.startDragAndDrop(TransferMode.COPY);
			ClipboardContent content = new ClipboardContent();
			content.putString(this.getType());
			db.setContent(content);

			event.consume();
		});
	}

	private String getType() {
		return type;
	}
}