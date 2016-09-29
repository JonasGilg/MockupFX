package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;

import java.awt.*;

/**
 * Converts Color to RGB
 */
public class ColorPropertyConverter extends PropertyConverter<Color> {
	@Override
	public Color fromConfig(Object value) {
		if (value != null) {
			int rgb = (int) value;
			return new Color(rgb);
		}
		return null;
	}

	@Override
	public Object toConfig(Color value) {
		if (value != null) {
			return value.getRGB();
		}
		return null;
	}
}
