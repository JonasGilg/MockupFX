package de.mockup.ui.gui.actions;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.service.impl.ProjectServiceImpl;
import de.mockup.ui.Application;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.gui.guiController.ProjectController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;

public class OpenProjectAction extends AbstractAction {

    public OpenProjectAction(ResourceBundle menuResources) {
        super(menuResources.getString("open"), "icons/openfile.png", false);
    }

    @Override
    public void handle(ActionEvent event) {
        final FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mockup",
                "*"+ ProjectServiceImpl.FILE_EXTENSION));
        File file = fc.showOpenDialog(Application.getPrimaryStage());
        if (file != null) {
            try {
                ProjectController.openProject(GuiToControllerFacade.loadProject(file.getPath()));
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        }
    }
}
