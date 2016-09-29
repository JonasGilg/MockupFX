package de.mockup.system.data.generator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests DataGenerator
 */
public class DoubleGeneratorTest {

    @Test
    public void testSize() {
        DoubleGenerator generator = new DoubleGenerator();
        List<Double> data = generator.generate(100);
        assertEquals(data.size(), 100);
    }

    @Test
    public void testRange() {
        double min = 20;
        double max = 100;
        DoubleGenerator generator = new DoubleGenerator();
        generator.setMinValue(min);
        generator.setMaxValue(max);

        List<Double> data = generator.generate(100);

        for (Double value : data) {
            assertTrue("Range not match!", value >= min && value <= max);
        }
    }

    @Test
    public void infTest() {
        double max = Double.MAX_VALUE;
        double min = -max;

        DoubleGenerator generator = new DoubleGenerator();
        generator.setMinValue(min);
        generator.setMaxValue(max);

        assertEquals(generator.getMaxValue(), Double.MAX_VALUE, 0);
        assertEquals(generator.getMinValue(), -max, 0);
        List<Double> data = generator.generate(20);

        assertEquals(generator.getMinValue(), Double.MIN_VALUE, 0);

        for (Double value : data) {
            assertTrue("Value is infinite!", !value.isInfinite());
        }
    }
}
