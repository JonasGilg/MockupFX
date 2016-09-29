package de.mockup.ui.gui.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class Action {

    private final String label;
    private final String icon;
    private boolean status = false;
    EventHandler<ActionEvent> event;

    private Button Button;
    private MenuItem myItem;

    public Action(String label, EventHandler<ActionEvent> event) {
        this(label, null, event);
    }

    public Action(String label, String icon, EventHandler<ActionEvent> event) {
        this.label = label;
        this.icon = icon;
        this.event = event;
    }

    public Action(String label, String icon, boolean status, EventHandler<ActionEvent> event) {
        this.label = label;
        this.icon = icon;
        this.status = status;
        this.event = event;
    }

    public Button createButton() {
        if (Button == null) {
            Button = new Button(label);
            Button.setTooltip(new Tooltip(label));
            Button.setDisable(status);
            Button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            Button.setStyle("-fx-background-color: transparent;");
            Button.setOnMouseEntered(e -> Button.setStyle("-fx-background-color: grey;"));
            Button.setOnMouseExited(e -> Button.setStyle("-fx-background-color: transparent;"));
            if (icon != null) {
                Button.setGraphic(getIcon());
                Button.setText("");
            }
            Button.setOnAction(event);
        }
        return Button;
    }

    public MenuItem createMenuItem() {
        if (myItem == null) {
            myItem = new MenuItem(label);
            myItem.setDisable(status);
            if (icon != null) {
                myItem.setGraphic(getIcon());
            }
            myItem.setOnAction(event);
        }
        return myItem;
    }

    public void setDisable(boolean status) {
        if (myItem != null) {
            myItem.setDisable(status);
        }
        if (Button != null) {
            Button.setDisable(status);
        }
    }

    ImageView getIcon() {
        if (icon != null) {
            ImageView a = new ImageView(icon);
            a.setFitHeight(18);
            a.setFitWidth(18);
            a.setPreserveRatio(true);
            a.setSmooth(true);
            a.setCache(true);
            return a;
        }
        return null;
    }

    String getLabel() {
        return label;
    }

    Button getButton() {
        return Button;
    }

    public void setButton(Button button) {
        this.Button = button;
    }

    MenuItem getMyItem() {
        return myItem;
    }

    void setMyItem(MenuItem myItem) {
        this.myItem = myItem;
    }

    boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
