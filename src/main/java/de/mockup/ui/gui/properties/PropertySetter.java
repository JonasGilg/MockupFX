package de.mockup.ui.gui.properties;

/**
 * Offers the implementing class a convenient method to use a simple functionpointer to send a new Property value
 *
 */
@FunctionalInterface
public interface PropertySetter {
	void sendNewValue(Runnable r);
}
