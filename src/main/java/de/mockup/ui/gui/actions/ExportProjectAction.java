package de.mockup.ui.gui.actions;

import de.mockup.system.exceptions.SystemException;
import de.mockup.ui.Application;
import de.mockup.ui.controller.logic.Utility;
import de.mockup.ui.gui.guiController.WindowManager;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;

public class ExportProjectAction extends AbstractAction {

    private static final ResourceBundle MENU_BUNDLE = ResourceBundle.getBundle("properties.MenuBundle");

    public ExportProjectAction(ResourceBundle menuResources) {
        super(menuResources.getString("exportToJPEG"), "icons/file-export.png", true);
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(MENU_BUNDLE.getString("saveAsJPEG"));
        File file = chooser.showSaveDialog(Application.getPrimaryStage());
        if (file != null) {
            String datapath = file.getAbsolutePath();
            try {
                Utility.componentToPNG(WindowManager.get().getWindow(WorkingSurface.class).getOverlay(), datapath);
            } catch (SystemException e) {
                //TODO Add MSG Dialogs for Errors and Messages
            }
        }
    }
}
