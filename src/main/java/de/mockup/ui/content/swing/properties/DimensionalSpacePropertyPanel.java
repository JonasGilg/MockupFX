package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.helper.GlobalBundle;

import javax.swing.*;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel displaying information such as location and size
 *
 */
public class DimensionalSpacePropertyPanel extends AbstractPropertyPanel {
	
	@Override protected void optionalConstructor(){
		if(!EXPAND_MAP.containsKey(this.getClass())){
			EXPAND_MAP.put(this.getClass(), true);
		}
	}

	public DimensionalSpacePropertyPanel(final ContentComponent<? extends JComponent> comp) {
		super(PROPERTY_BUNDLE.getString("sizeandcoords"));

		JComponent jcomp = comp.getContent();

		// X & Y
		Property<Point> propLocation = new Property<>(PROPERTY_BUNDLE.getString("location"), comp, comp::setLocation, comp::getLocation);
		this.addToContent(SwingSettingFieldFactory.createForPoint(propLocation));

		// Size
		Property<Dimension> propSize = new Property<>(GlobalBundle.getString("size"), comp, comp::setSize, comp::getSize);
		this.addToContent(SwingSettingFieldFactory.createForDimension(propSize));

		// Preferred Size
		Property<Dimension> propPrefSize = new Property<>(PROPERTY_BUNDLE.getString("prefsize"), comp, jcomp::setPreferredSize,
				jcomp::getPreferredSize);
		this.addToContent(SwingSettingFieldFactory.createForDimension(propPrefSize));

		// Minimum Size
		Property<Dimension> propMinSize = new Property<>(PROPERTY_BUNDLE.getString("minsize"), comp, jcomp::setMinimumSize,
				jcomp::getMinimumSize);
		this.addToContent(SwingSettingFieldFactory.createForDimension(propMinSize));

		// Maximum Size
		Property<Dimension> propMaxSize = new Property<>(PROPERTY_BUNDLE.getString("maxsize"), comp, jcomp::setMaximumSize,
				jcomp::getMaximumSize);
		this.addToContent(SwingSettingFieldFactory.createForDimension(propMaxSize));

		// AlignmentX
		Property<Float> propAlignX = new Property<>(PROPERTY_BUNDLE.getString("alignX"), comp, jcomp::setAlignmentX, jcomp::getAlignmentX);
		this.addToContent(SwingSettingFieldFactory.createForFloat(propAlignX));

		// AlignmentY
		Property<Float> propAlignY = new Property<>(PROPERTY_BUNDLE.getString("alignY"), comp, jcomp::setAlignmentY, jcomp::getAlignmentY);
		this.addToContent(SwingSettingFieldFactory.createForFloat(propAlignY));
	}

}
