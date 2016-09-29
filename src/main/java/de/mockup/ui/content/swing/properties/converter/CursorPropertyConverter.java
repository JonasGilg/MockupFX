package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;

import java.awt.*;

public class CursorPropertyConverter extends PropertyConverter<Cursor> {

    @Override
    public Cursor fromConfig(Object value) {
        if (value != null) {
            int type = (int) value;
            return new Cursor(type);
            }
        return null;
    }

    @Override
    public Object toConfig(Cursor value) {
        if (value != null) {
            return value.getType();
        }
        return null;
    }
}
