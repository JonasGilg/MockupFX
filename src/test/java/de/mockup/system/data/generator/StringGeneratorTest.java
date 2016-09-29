package de.mockup.system.data.generator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringGeneratorTest {

    @Test
    public void testSize() {
        StringGenerator generator = new StringGenerator();
        List<String> data = generator.generate(100);
        assertEquals(data.size(), 100);
    }

    @Test
    public void nullTest() {
        StringGenerator generator = new StringGenerator();
        List<String> data = generator.generate(100);

        for (String s : data) {
            assertTrue(s != null);
            assertTrue(s.length() > 0);
        }
    }
}