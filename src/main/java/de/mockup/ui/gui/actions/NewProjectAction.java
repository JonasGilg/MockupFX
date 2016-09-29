package de.mockup.ui.gui.actions;

import de.mockup.system.exceptions.SystemException;
import de.mockup.ui.Application;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.windows.dialogs.NewProjectDialog;
import de.mockup.ui.gui.windows.helper.GlobalBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

public class NewProjectAction extends AbstractAction {

    private static final ResourceBundle NEW_PROJECT_BUNDLE = ResourceBundle.getBundle("properties.NewProject");

    public NewProjectAction(ResourceBundle menuResources) {
        super(menuResources.getString("newProject"), "icons/newproject.png", false);
    }

    @Override
    public void handle(ActionEvent event) {
        if (ProjectController.hasProject()) {
            Alert newProjectAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newProjectAlert.setTitle(NEW_PROJECT_BUNDLE.getString("title"));
            newProjectAlert.setHeaderText(NEW_PROJECT_BUNDLE.getString("header"));
            newProjectAlert.setContentText(NEW_PROJECT_BUNDLE.getString("content"));
            ButtonType saveButton = new ButtonType(NEW_PROJECT_BUNDLE.getString("save"));
            ButtonType cancelButton =
                    new ButtonType(GlobalBundle.getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType discardButton = new ButtonType(NEW_PROJECT_BUNDLE.getString("discard"), ButtonBar.ButtonData.YES);
            newProjectAlert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);
            Optional<ButtonType> result = newProjectAlert.showAndWait();
            if (result.isPresent()){
                if (result.get() == saveButton) {
                    try {
                        GuiToControllerFacade.saveProject();
                        new NewProjectDialog(Application.getPrimaryStage());
                    } catch (SystemException ignored) {

                    }
                }
                if (result.get() == discardButton) {
                    new NewProjectDialog(Application.getPrimaryStage());
                }
                if (result.get() == cancelButton) {
                    //canceled
                }
            }
        } else {
            new NewProjectDialog(Application.getPrimaryStage());
        }
    }
}
