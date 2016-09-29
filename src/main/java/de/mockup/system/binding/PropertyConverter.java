package de.mockup.system.binding;

/**
 * BaseClass to convert Property values.
 *
 */
public abstract class PropertyConverter<T> {

	public abstract T fromConfig(Object value);

	public abstract Object toConfig(T value);

}
