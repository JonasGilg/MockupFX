package de.mockup.ui.gui.windows.dialogs;

import java.awt.Toolkit;
import java.util.List;
import java.util.ResourceBundle;

import de.mockup.system.model.StylesheetModel;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.guiController.StylesheetController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * The basic window for ProjectStylesheets. This contains a List of Stylesheets on the left side.
 * On the right side is a tabView that holds the properties for different GUI-elements.
 * {@link de.mockup.ui.gui.windows.dialogs.ProjectStylesheet tabView}
 * @author Janos Vollmer, Julian Korten
 *
 */
public class ProjectStylesheetChooser extends Stage {

	/**
	 * BorderPane on the right side that holds the properties.
	 */
	private final ProjectStylesheet options;

	/**
	 * List to be displayed on the left side.
	 */
	private ObservableList<String> names;

    private static final ResourceBundle STYLE_BUNDLE = ResourceBundle.getBundle("properties.StyleSheetBundle");

    /**
     * Control-buttons
     */
    private Button save, delete, applyButton;

	public ProjectStylesheetChooser(Window owner) {
		super();
		initOwner(owner);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(true);
		setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setTitle(STYLE_BUNDLE.getString("project stylesheet"));

		options = new ProjectStylesheet();

		BorderPane content = new BorderPane();
		BorderPane selection = new BorderPane();
		Insets insets = new Insets(10, 10, 10, 10);

		// Get the existing stylesheets from the backend
		loadStylesheetNames();

		final ListView<String> list = new ListView<String>(names);
		addContextMenu(list);

		// Load the selected stylesheet from the project
		{
			StylesheetModel sm = ProjectController.getStylesheet();
			options.loadFromModel(sm);
			int sel = StylesheetController.find(sm);
			if (sel > -1) {
				list.getSelectionModel().select(sel);
			}
		}

		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				if(list.getSelectionModel().getSelectedIndex() < 0) {
					Stage warning = new Stage();
					warning.initModality(Modality.APPLICATION_MODAL);
					warning.setTitle(STYLE_BUNDLE.getString("error"));
					warning.setMinWidth(100);
					warning.setMinHeight(50);
					BorderPane message = new BorderPane();
					Label errorLabel = new Label(STYLE_BUNDLE.getString("project needs stylesheet"));
					message.setTop(errorLabel);
					Button errorButton = new Button(STYLE_BUNDLE.getString("ok"));
					errorButton.setOnAction(event -> warning.close());
					message.setCenter(errorButton);
					Scene wsc = new Scene(message);
					warning.setScene(wsc);
					warning.show();
					e.consume();
				}
			}
		});

		list.getSelectionModel().selectedItemProperty().addListener(event -> {
			int index = list.getSelectionModel().getSelectedIndex();
			StylesheetModel sm = StylesheetController.getStylesheet(index);
			options.loadFromModel(sm);
			if(index != -1) {
				save.setDisable(false);
				delete.setDisable(false);
				applyButton.setDisable(false);
			}
		});

		BorderPane.setMargin(list, insets);
		selection.setCenter(list);

		ButtonBar selectionButtons = new ButtonBar();
		selection.setBottom(selectionButtons);
		BorderPane.setMargin(selectionButtons, insets);
		Button create = new Button(STYLE_BUNDLE.getString("new"));
		create.setOnAction(event -> {
			StylesheetModel m = new StylesheetModel();
			m.setName((STYLE_BUNDLE.getString("new stylesheet")) + names.size());
			StylesheetController.addStylesheet(m);
			StylesheetController.saveStylesheets();
			loadStylesheetNames();
			list.setItems(names);
		});
		selectionButtons.getButtons().add(create);

		save = new Button(STYLE_BUNDLE.getString("save"));
		save.setOnAction(event -> {
			int index = list.getSelectionModel().getSelectedIndex();
			StylesheetModel sm = StylesheetController.getStylesheet(index);
			options.saveIntoModel(sm);
			StylesheetController.saveStylesheets();
		});
		selectionButtons.getButtons().add(save);

		delete = new Button(STYLE_BUNDLE.getString("delete"));
		delete.setOnAction(event -> {
			int index = list.getSelectionModel().getSelectedIndex();
			if(index >= 0) {
				StylesheetController.removeStylesheet(index);
				StylesheetController.saveStylesheets();
				loadStylesheetNames();
				list.setItems(names);
				save.setDisable(true);
				delete.setDisable(true);
				applyButton.setDisable(true);
			}
		});
		selectionButtons.getButtons().add(delete);
		content.setLeft(selection);
		content.setRight(options);

		ButtonBar buttonBar = new ButtonBar();

		applyButton = new Button(STYLE_BUNDLE.getString("ok"));
		applyButton.setOnAction(event -> {
			int index = list.getSelectionModel().getSelectedIndex();
			StylesheetModel sm = StylesheetController.getStylesheet(index);
			options.saveIntoModel(sm);
			ProjectController.setStylesheet(sm);
			StylesheetController.saveStylesheets();
			close();
		});
		buttonBar.getButtons().add(applyButton);

		content.setBottom(buttonBar);
		BorderPane.setMargin(buttonBar, insets);

		Scene sc = new Scene(content);
		setScene(sc);

		show();
	}

	/**
	 * Load Stylesheet names. Needed for the list view on the left side of the panel
	 */
	private void loadStylesheetNames() {
		names = FXCollections.observableArrayList();
		List<StylesheetModel> stylesheets = StylesheetController.getStylesheets();
		for(StylesheetModel sm : stylesheets) {
			names.add(sm.getName());
		}
	}

	/**
	 * Adds a contextmenu to the listView to change the name of Stylesheets
	 */
	private void addContextMenu(ListView<String> view) {
		final ContextMenu menu = new ContextMenu();
		MenuItem rename = new MenuItem("Umbenennen"); //TODO Internationalisierung
		menu.getItems().add(rename);

		rename.setOnAction(e -> {
			Stage renameDialog = new Stage();
			renameDialog.initModality(Modality.APPLICATION_MODAL);
			renameDialog.setTitle("Umbenennen"); //TODO Internationalisierung
			renameDialog.setMinWidth(100);
			renameDialog.setMinHeight(100);

			BorderPane renameDialogPane = new BorderPane();

			Label renameDialogLabel = new Label("Neuen Namen für Stylesheet eingeben:"); //TODO Internationalisierung
			renameDialogPane.setTop(renameDialogLabel);

			TextField renameDialogEntry = new TextField();
			renameDialogPane.setCenter(renameDialogEntry);

			ButtonBar renameDialogButtons = new ButtonBar();
			renameDialogPane.setBottom(renameDialogButtons);

			Button okButton = new Button(STYLE_BUNDLE.getString("ok"));
			Button cancelButton = new Button("Abbrechen"); //TODO Internationalisierung

			renameDialogButtons.getButtons().addAll(okButton, cancelButton);

			okButton.setOnAction(evt -> {
				String entry = renameDialogEntry.getText();
				if(entry.length() > 0) {
					StylesheetController.renameStylesheet(view.getSelectionModel().getSelectedItem(), entry);
					loadStylesheetNames();
					view.setItems(names);
					renameDialog.close();
				}
			});

			cancelButton.setOnAction(evt -> {
				renameDialog.close();
			});

			Scene sc = new Scene(renameDialogPane);
			renameDialog.setScene(sc);
			renameDialog.show();
		});

		view.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				menu.show(view, event.getScreenX(), event.getScreenY());
				event.consume();
			}

		});
	}
}
