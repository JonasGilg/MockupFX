package de.mockup.ui.content.swing.properties;

import de.mockup.ui.gui.properties.PropertySetter;

/**
 * PropertySetter specialized for Swing/AWT
 */
public interface SwingPropertySetter extends PropertySetter {

	/**
	 * executes the given function in the EventQueue
	 *
	 * @param r function to be executed in the EventQueue
	 */
	default void sendNewValue(Runnable r) {
		r.run();
	}
}
