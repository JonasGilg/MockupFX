package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;
import de.mockup.system.json.JSONObject;

import java.awt.*;

/**
 * Converts Dimension to JSON
 */
public class DimensionPropertyConverter extends PropertyConverter<Dimension> {

	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";

	@Override
	public Dimension fromConfig(Object value) {
		if (value != null) {
			JSONObject data = (JSONObject) value;
			return new Dimension(data.getInt(WIDTH), data.getInt(HEIGHT));
		}
		return null;
	}

	@Override
	public Object toConfig(Dimension value) {
		if (value != null) {
			JSONObject data = new JSONObject();
			data.put(WIDTH, value.getWidth());
			data.put(HEIGHT, value.getHeight());
			return data;
		}
		return null;
	}
}
