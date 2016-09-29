package de.mockup.ui.content.swing.properties.converter;

import de.mockup.system.binding.PropertyConverter;

import java.awt.*;

/**
 * Converts ComponentOrientation to String
 */
public class ComponentOrientationPropertyConverter extends PropertyConverter<ComponentOrientation> {

	private static final String LEFT_TO_RIGHT = "leftToRight";
	private static final String RIGHT_TO_LEFT = "rightToLeft";
    private static final String UNKNOWN = "unknown";

	@Override
	public ComponentOrientation fromConfig(Object value) {
        if (value != null) {
            if (value.equals(RIGHT_TO_LEFT)) {
                return ComponentOrientation.RIGHT_TO_LEFT;
            }
            if (value.equals(LEFT_TO_RIGHT)) {
                return ComponentOrientation.LEFT_TO_RIGHT;
            }
        }

		return ComponentOrientation.UNKNOWN;
	}

	@Override
	public Object toConfig(ComponentOrientation value) {
        if (value != null) {
            if (value.equals(ComponentOrientation.RIGHT_TO_LEFT)) {
                return RIGHT_TO_LEFT;
            }
            if (value.equals(ComponentOrientation.LEFT_TO_RIGHT)) {
                return LEFT_TO_RIGHT;
            }
        }

		return UNKNOWN;
	}
}
