package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;

import javax.swing.*;
import java.net.URI;

/**
 * Converts ImageIcon to String for storage.
 */
public class ImageIconPropertyConverter extends PropertyConverter<Icon> {
	@Override
	public ImageIcon fromConfig(Object value) {
		ImageIcon icon = null;
		if (value != null) {
			try {
				icon = new ImageIcon(new URI((String) value).toURL());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return icon;
	}

	@Override
	public Object toConfig(Icon icon) {
		if (icon != null) {
			return ((ImageIcon) icon).getDescription();
		}
		return null;
	}

}
