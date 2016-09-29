package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingSlider;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JSlider
 */
class JSliderPropertyPanel extends AbstractPropertyPanel{

	public JSliderPropertyPanel(final SwingSlider slider) {
		super("JSlider "+ PROPERTY_BUNDLE.getString("properties"));

		JSlider jslider = new JSlider();
		
		//Orientation TODO spezialfall
		Property<Integer> propOrientation = new Property<>(PROPERTY_BUNDLE.getString("orientation"), slider,
				jslider::setOrientation, jslider::getOrientation);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propOrientation));
		
		//Inverted
		Property<Boolean> propInverted = new Property<>(PROPERTY_BUNDLE.getString("inverted"), slider,
				jslider::setInverted, jslider::getInverted);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propInverted));
		
		//Minimum
		Property<Integer> propMinimum = new Property<>(PROPERTY_BUNDLE.getString("min"), slider,
				jslider::setMinimum, jslider::getMinimum);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propMinimum));
		
		//Maximum
		Property<Integer> propMaximum = new Property<>(PROPERTY_BUNDLE.getString("max"), slider,
				jslider::setMaximum, jslider::getMaximum);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propMaximum));
		
		//PaintTicks
		Property<Boolean> propPaintTicks = new Property<>(PROPERTY_BUNDLE.getString("paintticks"), slider,
				jslider::setPaintTicks, jslider::getPaintTicks);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propPaintTicks));
		
		//MinorTickSpacing
		Property<Integer> propMinorTickSpacing = new Property<>(PROPERTY_BUNDLE.getString("mintickspace"), slider,
				jslider::setMinorTickSpacing, jslider::getMinorTickSpacing);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propMinorTickSpacing));
		
		//MajorTickSpacing
		Property<Integer> propMajorTickSpacing = new Property<>(PROPERTY_BUNDLE.getString("majtickspace"), slider,
				jslider::setMajorTickSpacing, jslider::getMajorTickSpacing);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propMajorTickSpacing));
		
		//Snap To Ticks
		Property<Boolean> propSnapToTicks = new Property<>(PROPERTY_BUNDLE.getString("snaptoticks"), slider,
				jslider::setSnapToTicks, jslider::getSnapToTicks);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propSnapToTicks));
		
		//Extent
		Property<Integer> propExtent = new Property<>(PROPERTY_BUNDLE.getString("extent"), slider,
				jslider::setExtent, jslider::getExtent);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propExtent));
		
		//PaintTrack
		Property<Boolean> propPaintTrack = new Property<>(PROPERTY_BUNDLE.getString("painttrack"), slider,
				jslider::setPaintTrack, jslider::getPaintTrack);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propPaintTrack));
		
		
		//Paint Labels
		Property<Boolean> propPaintLabels = new Property<>(PROPERTY_BUNDLE.getString("paintlabels"), slider,
				jslider::setPaintLabels, jslider::getPaintLabels);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propPaintLabels));
		
	}

}