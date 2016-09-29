package de.mockup.system.data.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DoubleGenerator extends DataGenerator<Double>{

    private double minValue = Double.MIN_VALUE;
    private double maxValue = Double.MAX_VALUE;

    @Override
    public List<Double> generate(int count) {
        List<Double> data = new ArrayList<>(count);
        if(Double.valueOf(maxValue-minValue).isInfinite()){
            minValue = Double.MIN_VALUE;
            maxValue = Double.MAX_VALUE;
        }
        for (int i = 0; i < count; i++) {
            data.add(ThreadLocalRandom.current().nextDouble(minValue, maxValue));
        }
        return data;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
