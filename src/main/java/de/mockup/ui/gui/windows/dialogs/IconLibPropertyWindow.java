package de.mockup.ui.gui.windows.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Creates the Property-Window/frame for the system- and graphicslibrary
 */
public class IconLibPropertyWindow extends VBox {

    private static final double PREF_WIDTH = 100;
    private static final double PREF_SPACING = 20;

    public IconLibPropertyWindow() {
        this.setPadding(new Insets(PREF_SPACING));
        setStyle("-fx-border-style: solid");
        initPropertyWindow();
    }

    private void initPropertyWindow() {
        VBox propLayout = new VBox(5);

        HBox box1 = new HBox(PREF_SPACING);
        Label name = new Label("Name: ");
        name.setPrefWidth(PREF_WIDTH);
        TextField nameTF = new TextField("Dummy-name");
        nameTF.setEditable(false);
        box1.setPadding(new Insets(PREF_SPACING));

        HBox box2 = new HBox(PREF_SPACING);
        Label file = new Label("Datei: ");
        file.setPrefWidth(PREF_WIDTH);
        TextField fileTF = new TextField("Dummy-file");
        fileTF.setEditable(false);
        box2.setPadding(new Insets(PREF_SPACING));

        HBox box3 = new HBox(PREF_SPACING);
        Label size = new Label("Groesse: ");
        size.setPrefWidth(PREF_WIDTH);
        TextField sizeTF = new TextField("Dummy-size");
        sizeTF.setEditable(false);
        box3.setPadding(new Insets(PREF_SPACING));

        HBox box4 = new HBox(PREF_SPACING);
        Label category = new Label("Kategorie: ");
        category.setPrefWidth(PREF_WIDTH);
        TextField categoryTF = new TextField("Dummy-Category");
        categoryTF.setEditable(false);
        box4.setPadding(new Insets(PREF_SPACING));

        Label description = new Label("Beschreibung: ");
        description.setPrefWidth(PREF_WIDTH);
        TextArea descriptionTA = new TextArea("Dummy-Description");
        descriptionTA.setPrefHeight(400);
        descriptionTA.setWrapText(true);

        box1.getChildren().addAll(name, nameTF);
        box2.getChildren().addAll(file, fileTF);
        box3.getChildren().addAll(size, sizeTF);
        box4.getChildren().addAll(category, categoryTF);

        this.getChildren().addAll(box1, box2, box3, box4, description, descriptionTA);
    }

}
