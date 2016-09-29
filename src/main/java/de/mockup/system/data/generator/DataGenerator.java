package de.mockup.system.data.generator;

import java.util.List;

/**
 * Abstract class for DataGenerators.
 */
public abstract class DataGenerator<T> {

	public abstract List<T> generate(int count);

}
