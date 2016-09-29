package de.mockup.ui.controller.logic;

import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.IconModel;
import de.mockup.system.service.IconLibraryService;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * This class contains settings for the iconlibframe and contains business logic
 */
public class IconlibSettings {

    /**
     * Sets the Extensions for the filechooser for adding icons.
     * The filter is on jpg and png files
     *
     * @param fc given filechooser from View
     */
    public static synchronized void configureIconFilechooser(FileChooser fc) {
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    /**
     * Creates an Iconmodel to save it permanently to the project
     *
     *
     * @param f the icon-file to be saved
     * @return whether saving has worked or not
     */
    public static synchronized boolean createAndSavePersistent(File f) {

        IconLibraryService ILS = Bundle.getService(IconLibraryService.class);
        IconModel model = new IconModel();
        try {
            ILS.storeIcon(f.getPath(), model);
            return true;
        } catch (SystemException e) {
            e.printStackTrace();
            return false;
        }

    }

}
