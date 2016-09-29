package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;
import de.mockup.system.json.JSONObject;

import java.awt.*;

/**
 * Converts Fonts to JSON
 */
public class FontPropertyConverter extends PropertyConverter<Font> {

	private static final String NAME = "name";
	private static final String STYLE = "style";
	private static final String SIZE = "size";

	@Override
	public Font fromConfig(Object value) {
		if (value != null) {
			JSONObject data = (JSONObject) value;
			return new Font(data.getString(NAME), data.getInt(STYLE), data.getInt(SIZE));
		}
		return null;
	}


	@Override
	public Object toConfig(Font value) {
		if (value != null) {
			JSONObject data = new JSONObject();
			data.put(NAME, value.getName());
			data.put(STYLE, value.getStyle());
			data.put(SIZE, value.getSize());
			return data;
		}
		return null;
	}
}
