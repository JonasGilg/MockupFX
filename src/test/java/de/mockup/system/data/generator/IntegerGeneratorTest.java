package de.mockup.system.data.generator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests IntegerGenerator
 */
public class IntegerGeneratorTest {

	@Test
	public void testSize() {
		IntegerGenerator generator = new IntegerGenerator();
		List<Integer> data = generator.generate(100);
		assertEquals(data.size(), 100);
	}

	@Test
	public void testRange() {
		int min = 20;
		int max = 100;
		IntegerGenerator generator = new IntegerGenerator();
		generator.setMinValue(min);
		generator.setMaxValue(max);

		List<Integer> data = generator.generate(100);

		for (Integer value : data) {
			assertTrue("Range not match!", value >= min && value <= max);
		}

	}
}
