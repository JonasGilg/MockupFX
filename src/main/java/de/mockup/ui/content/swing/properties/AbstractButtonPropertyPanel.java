package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for AbstractButtons
 */
class AbstractButtonPropertyPanel extends AbstractPropertyPanel{

	public AbstractButtonPropertyPanel(final SwingComponent<? extends AbstractButton> comp){
		super("AbstractButton "+ PROPERTY_BUNDLE.getString("properties"));

		AbstractButton jcomp = comp.getContent();

		//Text
		Property<String> propText = new Property<>(PROPERTY_BUNDLE.getString("text"), comp, jcomp::setText, jcomp::getText);
		this.addToContent(SwingSettingFieldFactory.createForString(propText));
		
		//BorderPainted
		Property<Boolean> propBorderPainted = new Property<>(PROPERTY_BUNDLE.getString("isborder"), comp,
				jcomp::setBorderPainted, jcomp::isBorderPainted);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propBorderPainted));
		
		//FocusPainted
		Property<Boolean> propFocusPainted = new Property<>(PROPERTY_BUNDLE.getString("isfocus"), comp,
				jcomp::setFocusPainted, jcomp::isFocusPainted);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propFocusPainted));
		
		//ContentAreaFilled
		Property<Boolean> propContentAreaFilled = new Property<>(PROPERTY_BUNDLE.getString("contentfill"), comp,
				jcomp::setContentAreaFilled, jcomp::isContentAreaFilled);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propContentAreaFilled));
		
		//Selected
		Property<Boolean> propSelected = new Property<>(PROPERTY_BUNDLE.getString("isSelected"), comp,
				jcomp::setSelected, jcomp::isSelected);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propSelected));
		
		//Horizontal Alignment
		Property<Integer> propHAlign = new Property<>(PROPERTY_BUNDLE.getString("alignH"), comp,
				jcomp::setHorizontalAlignment, jcomp::getHorizontalAlignment);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propHAlign));
		
		//Vertical Alignment
		Property<Integer> propVAlign = new Property<>(PROPERTY_BUNDLE.getString("alignV"), comp,
				jcomp::setVerticalAlignment, jcomp::getVerticalAlignment);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propVAlign));
		
		//Horizontal TextPosition
		Property<Integer> propHText = new Property<>(PROPERTY_BUNDLE.getString("textposH"), comp,
				jcomp::setHorizontalTextPosition, jcomp::getHorizontalTextPosition);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propHText));
		
		//Vertical TextPosition
		Property<Integer> propVText = new Property<>(PROPERTY_BUNDLE.getString("textposV"), comp,
				jcomp::setVerticalTextPosition, jcomp::getVerticalTextPosition);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propVText));
		
		//Icon TextGap
		Property<Integer> propIconTextGap = new Property<>(PROPERTY_BUNDLE.getString("icontextgap"), comp,
				jcomp::setIconTextGap, jcomp::getIconTextGap);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propIconTextGap));
		
		//Icon
		Property<Icon> propIcon = new Property<>(PROPERTY_BUNDLE.getString("icon"), comp, jcomp::setIcon, jcomp::getIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propIcon));
		
		//Selected Icon
		Property<Icon> propSelectedIcon = new Property<>(PROPERTY_BUNDLE.getString("selectedicon"), comp,
				jcomp::setSelectedIcon, jcomp::getSelectedIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propSelectedIcon));
		
		//Pressed Icon
		Property<Icon> propPressedIcon = new Property<>(PROPERTY_BUNDLE.getString("pressedicon"), comp,
				jcomp::setPressedIcon, jcomp::getPressedIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propPressedIcon));
		
		//Disabled Icon
		Property<Icon> propDisabledIcon = new Property<>(PROPERTY_BUNDLE.getString("disabledicon"), comp,
				jcomp::setDisabledIcon, jcomp::getDisabledIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propDisabledIcon));
		
		//Disabled Selection Icon
		Property<Icon> propDisabledSelectedIcon = new Property<>(PROPERTY_BUNDLE.getString("disSelecticon"), comp,
				jcomp::setDisabledSelectedIcon, jcomp::getDisabledSelectedIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propDisabledSelectedIcon));
		
		//Rollover Icon
		Property<Icon> propRolloverIcon = new Property<>(PROPERTY_BUNDLE.getString("rollovericon"), comp,
				jcomp::setRolloverIcon, jcomp::getRolloverIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propRolloverIcon));
		
		//RolloverSelected Icon
		Property<Icon> propRolloverSelectedIcon = new Property<>(PROPERTY_BUNDLE.getString("rolloverSelicon"), comp,
				jcomp::setRolloverSelectedIcon, jcomp::getRolloverSelectedIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propRolloverSelectedIcon));
		
		//MultiClick Threshhold
		Property<Long> propMultiClick = new Property<>(PROPERTY_BUNDLE.getString("multiclick"), comp,
				jcomp::setMultiClickThreshhold, jcomp::getMultiClickThreshhold);
		this.addToContent(SwingSettingFieldFactory.createForLong(propMultiClick));
		
		//RollOverEnabled
		Property<Boolean> proprollOverEnabled = new Property<>(PROPERTY_BUNDLE.getString("rolloveron"), comp,
				jcomp::setRolloverEnabled, jcomp::isRolloverEnabled);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(proprollOverEnabled));
		
		//Margin
		Property<Insets> propMargin = new Property<>(PROPERTY_BUNDLE.getString("margin"), comp,
				jcomp::setMargin, jcomp::getMargin);
		this.addToContent(SwingSettingFieldFactory.createForInsets(propMargin));
	}
}