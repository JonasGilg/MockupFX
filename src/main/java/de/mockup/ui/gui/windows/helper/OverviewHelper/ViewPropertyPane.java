package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.annotations.NeedsI18n;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
 * BorderPane that shows the properties of the currently selected View.
 * It lets the user edit some Properties like 'name' and 'description'
 * Instantiated by PropertyPane.java
 */
public class ViewPropertyPane extends BorderPane {

	private final EditButton nameButton;
	private final EditButton descriptionButton;
	private final Text nameText;
	private final Text typeText;
	private final Text dateText;
	private final TextField nameField;
	private final TextArea descriptionText;
	private final ViewItem item;


	public ViewPropertyPane(ViewItem item) {

		this.item = item;
		System.out.println(PropertyWindow.get().getWidth());

		GridPane gridPane = new GridPane();
		gridPane.setHgap(0);
		gridPane.setVgap(10);
		//1. column
		@NeedsI18n
		Label nameLabel = new Label("Name");
		Label typeLabel = new Label("Typ");
		Label dateLabel = new Label("Datum");
		Label descriptionLabel = new Label("Beschreibung");
		//2. column
		nameField = new TextField("");
		nameText = new Text(item.getContentView().getTitle());
		StackPane nameStack = new StackPane(nameField, nameText);
		StackPane.setAlignment(nameText, Pos.CENTER_LEFT);
		nameField.toBack();
		nameField.setVisible(false);
		nameText.toFront();
		nameText.setVisible(true);
		typeText = new Text(ProjectController.getType() + item.getContentView().getType());
		dateText = new Text(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(item.getContentView().getLastModifyDate()));

		//dateText= new Text(item.getContentView().getComponentModel().getCreatedOn().toString());//TODO
		//3. column

		nameButton = new EditButton();


		nameButton.setOnAction(e -> changeName());

		descriptionButton = new EditButton();
		//descriptionButton.setGraphic(new ImageView("icons/rename_clean.png"));
		descriptionButton.setOnAction(e -> changeDescription());

		//add to gridpane
		gridPane.addColumn(0, nameLabel, typeLabel, dateLabel, descriptionLabel);
		gridPane.addColumn(1, nameStack, typeText, dateText, new Label());
		gridPane.addColumn(2, nameButton, new Label(), new Label(), descriptionButton);


		this.setTop(gridPane);

		descriptionText = new TextArea(item.getContentView().getDescriptionText());
		descriptionText.setEditable(false);
		descriptionText.setWrapText(true);
		descriptionText.getStyleClass().add("descriptionTextArea");
		this.setCenter(descriptionText);

		Button deleteButton = new Button("delete");
		deleteButton.getStyleClass().add("deleteButton");

		deleteButton.setOnAction(e -> {
			ProjectController.deleteView(item.getContentView());
			PropertyWindow.get().loadOverViewProjectProperties();
			WorkingSurface.get().loadOverViewGrid();
		});

		this.setBottom(deleteButton);


	}

	/*methods to edit properties*/
	private void changeName() {

		nameField.setText(item.getContentView().getTitle());
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

	private void nameEntered(boolean bool) {
		if (bool) {
			setName(nameField.getText());
			nameText.setText(nameField.getText());
		}
		toggleNameStack(true);
		nameButton.setOnAction(e -> changeName());


	}

	private void setName(String name) {
		item.setText(name);
		item.getContentView().setTitle(name);

		((ViewModel) item.getContentView().getComponentModel()).setTitle(name);
		item.getContentView().notifyObservers();


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

	//###################################################################
	private void changeDescription() {
		descriptionText.setEditable(true);
		descriptionButton.setAcceptMode();
		descriptionButton.setOnAction(e -> descriptionSave());
		descriptionText.requestFocus();
		descriptionText.selectAll();
	}

	private void descriptionSave() {
		descriptionButton.setEditMode();
		descriptionButton.setOnAction(e -> changeDescription());
		descriptionText.setEditable(false);
		item.getContentView().setDescriptionText(descriptionText.getText());
	}
}
