package de.mockup.ui.gui.windows.helper.TabPaneHelper;

import de.mockup.ui.gui.guiController.ProjectController;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
/**
 *
 * Funktion: Faked "+"-Button to add new Views
 * Verbindung: Part of TabPane
 *
 */
public class PlusTab extends Tab {
    public PlusTab(){
        Button button = new Button("+");
        //setText("+");
        //this.setOnSelectionChanged(e-> {ProjectController.addNewView();});

        button.setOnAction(event -> ProjectController.addNewView());
        setGraphic(button);
        setStyle("-fx-padding: 0 0 0 0;");
        setClosable(false);
    }


}
