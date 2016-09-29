package de.mockup.ui.gui.properties;

import de.mockup.system.binding.PropertyConverter;

/**
 * Default PropertyConverter. Used for all non object type like Strings and Numbers...
 */
public class DefaultPropertyConverter<T> extends PropertyConverter<T> {

	@Override
	public T fromConfig(Object value) {
		return (T) value;
	}

	@Override
	public Object toConfig(T value) {
		return value;
	}
}
