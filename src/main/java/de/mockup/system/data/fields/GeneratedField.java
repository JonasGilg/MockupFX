package de.mockup.system.data.fields;

import de.mockup.system.data.generator.DataGenerator;

import java.util.List;

/**
 * Class for generated DataFields using DataGenerator
 */
public class GeneratedField<T> extends DataField<T> {

	private DataGenerator<T> generator;

	@Override
	public List<T> getData(int count) {
		return generator.generate(count);
	}
}
