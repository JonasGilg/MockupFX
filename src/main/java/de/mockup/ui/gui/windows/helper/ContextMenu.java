package de.mockup.ui.gui.windows.helper;

import de.mockup.system.exceptions.SystemException;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.controller.logic.Management;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.guiController.UserActionController;
import de.mockup.ui.gui.windows.Toolbar;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * the context menu every MComponent will show on right clicking it
 * <p>
 * - has some basic menuItems, that every MComponent will get <br>
 * - specific Items have to be added by the MComponent itself <br>
 * - Methods to add/remove Items
 */
public class ContextMenu extends javafx.scene.control.ContextMenu {

    public ContextMenu(ContentComponent component) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.MenuBundle");
        MenuItem itemCopy = new MenuItem(resourceBundle.getString("copy"));
        MenuItem itemCut = new MenuItem(resourceBundle.getString("cut"));
        MenuItem itemPaste = new MenuItem(resourceBundle.getString("paste"));
        MenuItem itemDelete = new MenuItem(resourceBundle.getString("delete"));

        itemCut.setOnAction(e -> UserActionController.cut());

        this.getItems().add(itemCut);


        itemCopy.setOnAction(e -> UserActionController.copy());

        this.getItems().add(itemCopy);

        itemPaste.setOnAction(e -> {
            ContentContainer pastedOn;
            if (component instanceof ContentContainer) {
                pastedOn = (ContentContainer) component;
            } else {
                pastedOn = component.getParent();
            }
            UserActionController.paste(pastedOn,
                    new Point((int) getX() - WorkingSurface.get().getTabCoordinates().x, (int) getY()
                            - WorkingSurface.get().getTabCoordinates().y));
        });

        this.getItems().add(itemPaste);


        if (!(component instanceof ContentView)) {
            this.addSeparator();
            MenuItem createTemplate = new MenuItem(resourceBundle.getString("create_template"));
            createTemplate.setOnAction(e -> {
                TextInputDialog textInputDialog = new TextInputDialog("Name");
                textInputDialog.setTitle("Template erstellen");
                textInputDialog.setHeaderText("Geben Sie einen Namen ein:");
                Optional<String> result = textInputDialog.showAndWait();
                if (result.isPresent()) {
                    String name = result.get();
                    try {
                        Management.saveTemplate(name, component);
                        Toolbar.get().refreshTemplates();
                    } catch (SystemException ex) {
                        //damnnnnnn...
                    }
                }
            });
            this.getItems().add(createTemplate);
            this.addSeparator();
            itemDelete.setOnAction(e -> ProjectController.deleteComponent(component));

            this.getItems().add(itemDelete);
        }
    }

    public void addMenuItemAt(MenuItem item, int position) {
        this.getItems().add(position - 1, item);

    }

    private void addSeparator() {
        this.getItems().add(new SeparatorMenuItem());

    }
}
