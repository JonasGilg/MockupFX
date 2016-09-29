package de.mockup.ui.gui.windows.dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;

/**
 * Creates the frame for Categories for the icons
 */
public class IconLibCategoryFrame extends Stage {

    VBox mainPanel = new VBox(3);

    /**
     * Creates the frame in constructor
     *
     * @param owner
     */
    public IconLibCategoryFrame(Window owner) {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setX(100);
        setY(100);
        setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.3);
        setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.6);

        Scene s = new Scene(mainPanel);
        initFrameItems();
        setScene(s);
        setTitle("Kategorien");

        show();
    }

    /**
     * initializes the frame items in the category-frame
     */
    private void initFrameItems() {
        TextField categoryName = new TextField();
        Button addCategory = new Button("Kategorie hinzufügen");
        HBox b1 = new HBox(categoryName, addCategory);
        b1.setSpacing(10);

        ListView<String> categoryListView = new ListView<String>();
        ObservableList<String> categoryNameList = FXCollections.observableArrayList("TestCategory 1", "TestCategory 2", "TestCategory 3");
        categoryListView.setItems(categoryNameList);
        Button deleteCategoryFromView = new Button("Löschen");
        HBox b2 = new HBox(categoryListView, deleteCategoryFromView);
        b2.setSpacing(10);

        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Abbrechen");
        HBox b3 = new HBox(okButton, cancelButton);
        b3.setPrefWidth(getMaxWidth() * 0.8);
        b3.setSpacing(10);

        mainPanel.setSpacing(20);
        mainPanel.setPadding(new Insets(20));
        mainPanel.getChildren().addAll(b1, b2, b3);
    }
}
