package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.annotations.NeedsI18n;
import de.mockup.ui.gui.guiController.ProjectController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;

/**
 * BorderPane to show ProjectProperties in PropertyWindow.
 * Gets layered over the actual properties whenever the view overview is first called.
 */
class ProjectPropertyPane extends BorderPane {
	private final EditButton nameButton;
	private final EditButton descriptionButton;
	private final Text nameText;
	private final TextField nameField;
	private final TextArea descriptionText;

	public ProjectPropertyPane() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(0);
		gridPane.setVgap(10);
		@NeedsI18n
		Label nameLabel = new Label("Name");
		Label typeLabel = new Label("Typ");
		Label dateLabel = new Label("Datum");
		Label pathLabel = new Label("Pfad");
		Label descriptionLabel = new Label("Beschreibung");

		nameText = new Text(ProjectController.getProjectName());
		nameField = new TextField();
		Text typeText = new Text(ProjectController.getType().toString());
		Text dateText = new Text(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ProjectController.getDate()));
		Text pathText = new Text(ProjectController.getSavepath());

		nameButton = new EditButton();
		nameButton.setOnAction(e -> changeName());
		StackPane nameStack = new StackPane(nameField, nameText);
		StackPane.setAlignment(nameText, Pos.CENTER_LEFT);
		nameField.toBack();
		nameField.setVisible(false);
		nameText.toFront();
		nameText.setVisible(true);


		descriptionButton = new EditButton();
		descriptionButton.setOnAction(e -> descriptionEditMode());

		gridPane.addColumn(0, nameLabel, typeLabel, dateLabel, pathLabel, descriptionLabel);
		gridPane.addColumn(1, nameStack, typeText, dateText, pathText);
		gridPane.addColumn(2, nameButton, new Label(), new Label(), new Label(), descriptionButton);

		this.setTop(gridPane);

		descriptionText = new TextArea(ProjectController.getProjectDescriptionText());
		descriptionText.setEditable(false);
		descriptionText.setWrapText(true);
		this.setCenter(descriptionText);
	}

	private void changeName() {
		nameField.setText(ProjectController.getProjectName());
		toggleNameStack(false);
		nameButton.setOnAction(event -> nameEntered(true));
		nameField.requestFocus();
		nameField.setOnKeyPressed(this::enterHit);
	}

	private void enterHit(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			if (!nameField.getText().equals("")) {
				nameEntered(true);
			}
			nameEntered(false);
		}
		if (e.getCode().equals(KeyCode.ESCAPE)) {
			nameEntered(false);
		}
	}

	private void nameEntered(boolean b) {
		if (b) {
			ProjectController.setProjectName(nameField.getText());
			nameText.setText(nameField.getText());
		}
		toggleNameStack(true);
		nameButton.setOnAction(e -> changeName());
	}

	private void toggleNameStack(boolean textToFront) {
		if (textToFront) {
			nameText.toFront();
			nameText.setVisible(true);
			nameField.toBack();
			nameField.setVisible(false);
			nameButton.setEditMode();
		} else {
			nameText.toBack();
			nameText.setVisible(false);
			nameField.toFront();
			nameField.setVisible(true);
			nameButton.setAcceptMode();
		}
	}

	private void descriptionEditMode() {
		descriptionButton.setAcceptMode();
		descriptionText.setEditable(true);
		descriptionText.requestFocus();
		descriptionText.selectAll();
		descriptionButton.setOnAction(e -> descriptionChanged());

	}

	private void descriptionChanged() {
		descriptionButton.setEditMode();
		descriptionText.setEditable(false);
		ProjectController.setProjectDescription(descriptionText.getText());
		descriptionButton.setOnAction(e -> descriptionEditMode());
	}
}
