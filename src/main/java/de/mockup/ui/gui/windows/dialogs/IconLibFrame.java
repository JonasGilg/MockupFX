package de.mockup.ui.gui.windows.dialogs;

import de.mockup.system.exceptions.SystemException;
import de.mockup.ui.Application;
import de.mockup.ui.GuiToControllerFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;

/**
 * Creates the frame for the system- and graphicslibrary
 */
public class IconLibFrame extends Stage {

    private HBox mainPane = new HBox(3);
    private VBox menuBox = new VBox(2);

    /**
     * Creates the first basic frame and set actions for buttons
     *
     * @param owner
     * @throws SystemException
     */
    public IconLibFrame(Window owner) throws SystemException {
        super();

        Button icon;
        Button categories;
        Scene s;

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);

        setResizable(true);
        setX(100);
        setY(100);
        setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8);
        setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);

        s = new Scene(mainPane);
        setScene(s);

        setTitle("Symbol/Grafik-Bibliothek");

        categories = new Button("Kategorien");

        categories.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IconLibCategoryFrame catFrame = new IconLibCategoryFrame(Application.getPrimaryStage());
            }
        });

        icon = new Button("+ Icon");
        icon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                String absolutePath, directoryPath;

                GuiToControllerFacade.configIconFilechooser(fileChooser);
                fileChooser.setTitle("Füge Icon hinzu");
                File f = fileChooser.showOpenDialog(owner);

                if (f != null) {
                    if (GuiToControllerFacade.createIconAndSavePermanently(f)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Creating and saving Icon was successful");
                        alert.showAndWait();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error on creating and saving Icon");
                        alert.setContentText("Something went wrong with creating and saving the selected Icon.\n\n" +
                                "Please try again.");
                        alert.showAndWait();
                    }
                }
            }
        });

        menuBox.getChildren().add(categories);
        menuBox.getChildren().add(icon);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(30);

        mainPane.getChildren().add(new IconLibIcongrid());
        mainPane.getChildren().add(menuBox);
        mainPane.getChildren().add(new IconLibPropertyWindow());

        show();
    }

}
