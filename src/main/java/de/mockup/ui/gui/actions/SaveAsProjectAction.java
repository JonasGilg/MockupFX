package de.mockup.ui.gui.actions;

import de.mockup.ui.Application;
import de.mockup.ui.gui.guiController.ProjectController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;

public class SaveAsProjectAction extends AbstractAction {
    public SaveAsProjectAction(ResourceBundle menuResources) {
        super(menuResources.getString("saveAs"), "icons/save-as.png", true);
    }
    @Override
    public void handle(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName(ProjectController.getProjectName());
        fc.setInitialDirectory(new File(ProjectController.SAVEPATH + "/../"));
        File file = fc.showSaveDialog(Application.getPrimaryStage());
        if (file != null) {
            ProjectController.saveProjectAs(file.getName(), file.getAbsolutePath());
        }
    }
}
