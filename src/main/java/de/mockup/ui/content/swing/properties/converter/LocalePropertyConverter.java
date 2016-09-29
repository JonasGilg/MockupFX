package de.mockup.ui.content.swing.properties.converter;


import de.mockup.system.binding.PropertyConverter;

import java.util.Locale;

public class LocalePropertyConverter extends PropertyConverter<Locale> {

    @Override
    public Locale fromConfig(Object value) {
        Locale loc = null;
        if (value != null) {
            loc =  Locale.forLanguageTag((String) value);
        }
        return loc;
    }

    @Override
    public Object toConfig(Locale loc) {
        if (loc != null) {
            return loc.toLanguageTag();
        }

        return null;
    }
}
