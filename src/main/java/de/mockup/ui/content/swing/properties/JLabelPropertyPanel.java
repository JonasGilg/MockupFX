package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingLabel;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JLabels
 */
class JLabelPropertyPanel extends AbstractPropertyPanel {

	public JLabelPropertyPanel(final SwingLabel label) {
		super("JLabel "+PROPERTY_BUNDLE.getString("properties"));

		JLabel jlabel = label.getContent();

		// Text
		Property<String> propText = new Property<>(PROPERTY_BUNDLE.getString("text"), label, jlabel::setText, jlabel::getText);
		this.addToContent(SwingSettingFieldFactory.createForString(propText));

		// Horizontal Alignment
		Property<Integer> propHAlign = new Property<>(PROPERTY_BUNDLE.getString("alignH"), label, jlabel::setHorizontalAlignment,
				jlabel::getHorizontalAlignment);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propHAlign));

		// Vertical Alignment
		Property<Integer> propVAlign = new Property<>(PROPERTY_BUNDLE.getString("alignV"), label, jlabel::setVerticalAlignment,
				jlabel::getVerticalAlignment);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propVAlign));

		// Horizontal TextPosition
		Property<Integer> propHText = new Property<>(PROPERTY_BUNDLE.getString("textposH"), label, jlabel::setHorizontalTextPosition,
				jlabel::getHorizontalTextPosition);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propHText));

		// Vertical TextPosition
		Property<Integer> propVText = new Property<>(PROPERTY_BUNDLE.getString("textposV"), label, jlabel::setVerticalTextPosition,
				jlabel::getVerticalTextPosition);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propVText));

		// Icon TextGap
		Property<Integer> propIconTextGap = new Property<>(PROPERTY_BUNDLE.getString("icontextgap"), label, jlabel::setIconTextGap,
				jlabel::getIconTextGap);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propIconTextGap));

		// Icon
		Property<Icon> propIcon = new Property<>(PROPERTY_BUNDLE.getString("icon"), label, jlabel::setIcon, jlabel::getIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propIcon));

		// Disabled Icon
		Property<Icon> propDisabledIcon = new Property<>(PROPERTY_BUNDLE.getString("disabledicon"), label, jlabel::setDisabledIcon,
				jlabel::getDisabledIcon);
		this.addToContent(SwingSettingFieldFactory.createForIcon(propDisabledIcon));

		//TODO Displayed Icon muss mit keys ersetzt werden
		Property<Integer> propDisplayedMnemonic = new Property<>(PROPERTY_BUNDLE.getString("mnemonic"),label,
				jlabel::setDisplayedMnemonic,jlabel::getDisplayedMnemonic);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propDisplayedMnemonic));
	}
}
