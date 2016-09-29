package de.mockup.system.data.generator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringGenerator extends DataGenerator<String> {

    @Override
    public List<String> generate(int count) {
        List<String> data = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            data.add(i, new BigInteger(130, new Random()).toString(32));

        }
        return data;
    }
}
