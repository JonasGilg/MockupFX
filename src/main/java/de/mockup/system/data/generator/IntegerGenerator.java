package de.mockup.system.data.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates Integers
 */
public class IntegerGenerator extends DataGenerator<Integer> {

	private int minValue = Integer.MIN_VALUE;
	private int maxValue = Integer.MAX_VALUE;

	@Override
	public List<Integer> generate(int count) {
		List<Integer> data = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			data.add(ThreadLocalRandom.current().nextInt(minValue, maxValue));
		}
		return data;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}
