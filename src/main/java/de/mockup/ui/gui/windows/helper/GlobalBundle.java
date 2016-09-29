package de.mockup.ui.gui.windows.helper;

import java.util.ResourceBundle;

/**
 * Contains resources, that can be used globally.
 */
public class GlobalBundle {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("properties.BasicBundle");

	public static String getString(String key) {
		return bundle.getString(key);
	}
}
