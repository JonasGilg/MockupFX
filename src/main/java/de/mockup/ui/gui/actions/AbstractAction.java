package de.mockup.ui.gui.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

abstract class AbstractAction extends Action implements EventHandler<ActionEvent> {

    AbstractAction(String label, String icon, boolean status){
        super(label, icon, status, null);
        this.event = this;
    }

}
