package de.mockup.ui.gui.windows.helper.OverviewHelper;

import javafx.scene.control.Button;

/**
 * a button that toggles it image
 *
 * with 2 methods to change its state
 */
class EditButton extends Button
{

	public EditButton() {
		this.getStyleClass().add("editButton_edit");
		this.setMaxWidth(24);
		this.setMaxHeight(24);

	}
	public void setAcceptMode(){
		this.getStyleClass().clear();
		this.getStyleClass().add("editButton_accept");
	}
	public void setEditMode(){
		this.getStyleClass().clear();
        this.getStyleClass().add("editButton_edit");

	}
}
