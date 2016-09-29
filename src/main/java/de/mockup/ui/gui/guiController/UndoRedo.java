package de.mockup.ui.gui.guiController;

import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.MainFrame;

import javax.swing.undo.AbstractUndoableEdit;
import java.util.function.Consumer;

/**
 * This class enables to create an {@link AbstractUndoableEdit}. It is preferably used for simple {@link Property}
 * changes like color or size.
 *
 * @param <T> the {@link Property} to be changed eg. color
 */
public class UndoRedo<T> extends AbstractUndoableEdit {
	private Property<T> prop;
	private Object oldValue;
	private Object newValue;

	/**
	 * @param name     of the {@link Property}
	 * @param comp     component of which the {@link Property} is changed
	 * @param setter   the method that sets the {@link Property}
	 * @param oldValue the value before the change happened
	 * @param newValue the value after the change happened
	 */
	public UndoRedo(String name, ContentComponent comp, Consumer<T> setter, Object oldValue, Object newValue) {
		if(oldValue != null && !oldValue.equals(newValue)) {
			prop = new Property<>(name, comp, setter, null);
			this.oldValue = oldValue;
			this.newValue = newValue;
			MainFrame.get().setUndoRedoButtonState();
			GuiToControllerFacade.changeAction(this);
		}
	}

	/**
	 * Should only be called by the {@link javax.swing.undo.UndoManager} never manually!
	 */
	@Override public void undo() {
		super.undo();
		prop.set((T) oldValue);
		prop.getComponent().notifyObservers();
	}

	/**
	 * Should only be called by the {@link javax.swing.undo.UndoManager} never manually!
	 */
	@Override public void redo() {
		super.redo();
		prop.set((T) newValue);
		prop.getComponent().notifyObservers();
	}
}
