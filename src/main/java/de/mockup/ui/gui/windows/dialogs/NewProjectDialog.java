package de.mockup.ui.gui.windows.dialogs;

import de.mockup.ui.Application;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.content.ContentType;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.windows.HierarchyTree;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.ResourceBundle;

/**
 * The New Project creation wizard :)
 */
public class NewProjectDialog extends Stage {

    //------------Project Settings------------------------------

    private String projectName = ProjectController.PROJECT_NAME;
    private ContentType type = ProjectController.PROJECT_TYPE;
    private String savepath = ProjectController.SAVEPATH;
    private String lookAndFeel = ProjectController.lookAndFeel;

    //------------GUI components--------------------------------
    private final StackPane rootPane;
    private BorderPane firstPane;
    private LookAndFeelChooserPane secondPane;


    //---------------------------------------------
    private static final ResourceBundle NEW_PROJECT_BUNDLE = ResourceBundle.getBundle("properties.NewProject");
    private final DirectoryChooser dc;

    public NewProjectDialog(Window owner) {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);

        setWidth(800);
        setHeight(500);
        setTitle(NEW_PROJECT_BUNDLE.getString("createNewProject"));
        dc = new DirectoryChooser();

        initiateFirstPage();

        rootPane = new StackPane();
        rootPane.getChildren().add(firstPane);


        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("stylesheets/newProjectDialog.css");
        setScene(scene);
        show();
    }

    private void changePage(boolean toSecond) {
        if (toSecond) {
            firstPane.setVisible(false);
            firstPane.toBack();
            secondPane.setVisible(true);
            secondPane.toFront();
        } else {
            secondPane.setVisible(false);
            secondPane.toBack();
            firstPane.setVisible(true);
            firstPane.toFront();
        }
    }


    /**
     * initiates the view for project name, type and save path
     */
    private void initiateFirstPage() {

        firstPane = new BorderPane();
        HBox nameHBox = new HBox();
        nameHBox.getStyleClass().add("hbox");

        Text nameText = new Text(NEW_PROJECT_BUNDLE.getString("projectName"));
        TextField nameTextArea = new TextField(projectName);

        nameHBox.getChildren().addAll(nameText, nameTextArea);

        HBox saveHBox = new HBox();
        saveHBox.getStyleClass().add("hbox");
        Text saveText = new Text(NEW_PROJECT_BUNDLE.getString("savePath"));
        TextField saveTextArea = new TextField(savepath);
        Button saveButton = new Button(NEW_PROJECT_BUNDLE.getString("open"));
        saveButton.setOnAction(e -> {

            //TODO fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter());
            File saveDirectory = dc.showDialog(Application.getPrimaryStage());
            savepath = saveDirectory.getAbsolutePath();
            saveTextArea.setText(savepath);


        });
        saveHBox.getChildren().addAll(saveText, saveTextArea, saveButton);

        HBox typeHBox = new HBox();
        typeHBox.getStyleClass().add("hbox");
        Text typeText = new Text(NEW_PROJECT_BUNDLE.getString("projectType"));
        ComboBox<ContentType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(ContentType.SWING);/*TODO "html","javafx",....*/
        comboBox.getSelectionModel().select(ContentType.SWING);
        typeHBox.getChildren().addAll(typeText, comboBox);

        ButtonBar buttonBar = new ButtonBar();
        Button applyButton = new Button(NEW_PROJECT_BUNDLE.getString("next"));
        applyButton.setDefaultButton(true);
        applyButton.setOnAction(event -> {
            boolean error = false;
            if (nameTextArea.getText().equals("")) {
                showErrorDialog(NEW_PROJECT_BUNDLE.getString("errorName"));
                error = true;
            } else {
                projectName = nameTextArea.getText();
            }

            type = comboBox.getSelectionModel().getSelectedItem();

            //type = "swing";

            if (saveTextArea.getText().equals("")) {
                showErrorDialog(NEW_PROJECT_BUNDLE.getString("errorPath"));
                error = true;
            } else {
                savepath = saveTextArea.getText();
            }


            if (!error) {
                ProjectController.createNewProject(projectName, type, savepath, lookAndFeel);
                initiateSecondPage();
                changePage(true);

            }
        });
        Button cancelButton = new Button(NEW_PROJECT_BUNDLE.getString("cancel"));
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> {
            ProjectController.setProjectNull();
            close();
        });


        buttonBar.getButtons().addAll(cancelButton, applyButton);


        VBox rootVBox = new VBox(nameHBox, typeHBox, saveHBox);
        rootVBox.getStyleClass().add("vbox");
        firstPane.setCenter(rootVBox);
        firstPane.setBottom(buttonBar);


    }

	/**
	 * initates the second page, where you can choose a LookAndFeel
     *
     * (NOTE: has to be done AFTER creating a project on page1, otherwise LaF-Manager won't work)
     */
    private void initiateSecondPage() {

        secondPane = new LookAndFeelChooserPane();
        ButtonBar buttonBar = new ButtonBar();
        Button finishButton = new Button(NEW_PROJECT_BUNDLE.getString("finish"));
        finishButton.setDefaultButton(true);
        finishButton.setOnAction(event -> {
            secondPane.apply();
            ProjectController.lookAndFeel = GuiToContentFacade.LaF.getLaF();
            HierarchyTree.get().initTree(projectName);
            ProjectController.startTheProject();
            close();
        });

        Button backButton = new Button(NEW_PROJECT_BUNDLE.getString("back"));
        backButton.setOnAction(event -> changePage(false));

        Button cancelButton = new Button(NEW_PROJECT_BUNDLE.getString("cancel"));
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> {
            ProjectController.setProjectNull();
            close();
        });

        buttonBar.getButtons().addAll(cancelButton, backButton, finishButton);
        secondPane.setBottom(buttonBar);
        rootPane.getChildren().add(secondPane);


    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(NEW_PROJECT_BUNDLE.getString("error"));
        alert.setHeaderText(NEW_PROJECT_BUNDLE.getString("inputError"));
        alert.setContentText(message);
        alert.showAndWait();
    }
}

