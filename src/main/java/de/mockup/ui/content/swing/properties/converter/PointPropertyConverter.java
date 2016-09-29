package de.mockup.ui.content.swing.properties.converter;


import de.mockup.system.binding.PropertyConverter;
import de.mockup.system.json.JSONObject;

import java.awt.*;

public class PointPropertyConverter extends PropertyConverter<Point> {

    private static final String X = "x";
    private static final String Y = "y";

    @Override
    public Point fromConfig(Object value) {
        if (value != null) {
            JSONObject data = (JSONObject) value;
            return new Point(data.getInt(X), data.getInt(Y));
        }
        return null;
    }

    @Override
    public Object toConfig(Point value) {
        if (value != null) {
            JSONObject data = new JSONObject();
            data.put(X,value.getX());
            data.put(Y, value.getY());
            return data;
        }
        return null;
    }
}
