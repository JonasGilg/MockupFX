package de.mockup.ui.gui.actions;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;

public class CheckableAction extends Action {

    private boolean selected;
    private ChangeListener<Boolean> change;

    public CheckableAction(String label, String icon, boolean status, boolean selected, ChangeListener<Boolean> change) {
        super(label, icon, status, null);
        this.selected = selected;
        this.change = change;
    }

    //abstract void handleSelectionChange(boolean newvalue, boolean oldvalue);

    @Override
    public Button createButton() {
        if(getButton() == null) {
            ToggleButton button = new ToggleButton();
            //button.selectedProperty().addListener((observable, oldValue, newValue) -> handleSelectionChange(newValue, oldValue));
            button.setTooltip(new Tooltip(getLabel()));
            button.setDisable(isStatus());
            button.setSelected(selected);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setStyle("-fx-background-color: transparent;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: grey;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent;"));
            if (getIcon() != null) {
                button.setGraphic(getIcon());
                button.setText("");
            }
        }
        return getButton();
    }

    @Override
    public MenuItem createMenuItem() {
        if (getMyItem() == null) {
            CheckMenuItem item = new CheckMenuItem();
            item.selectedProperty().addListener(change);
            //item.selectedProperty().addListener((observable, oldValue, newValue) -> handleSelectionChange(newValue, oldValue));
            item.setGraphic(getIcon());
            item.setText(getLabel());
            item.setSelected(selected);
            item.setDisable(isStatus());
            setMyItem(item);
        }
        return getMyItem();
    }
}
