package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.text.JTextComponent;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JTextComponents
 */
class JTextComponentPropertyPanel extends AbstractPropertyPanel{

	public JTextComponentPropertyPanel(final SwingComponent<? extends JTextComponent> textcomp) {
		super("JTextComponent "+ PROPERTY_BUNDLE.getString("properties"));

		JTextComponent jtextcomp = textcomp.getContent();
		
		// Text
		Property<String> propText = new Property<>(PROPERTY_BUNDLE.getString("text"), textcomp, jtextcomp::setText, jtextcomp::getText);
		this.addToContent(SwingSettingFieldFactory.createForString(propText));
		
		//Disabled Text Color
		Property<Color> propDisabledTextColor = new Property<>(PROPERTY_BUNDLE.getString("discolor"), textcomp,
				jtextcomp::setDisabledTextColor, jtextcomp::getDisabledTextColor);
		this.addToContent(SwingSettingFieldFactory.createForColor(propDisabledTextColor));
		
		// Selected Text Color
		Property<Color> propSelectedTextColor = new Property<>(PROPERTY_BUNDLE.getString("selectedcolor"), textcomp,
				jtextcomp::setSelectedTextColor, jtextcomp::getSelectedTextColor);
		this.addToContent(SwingSettingFieldFactory.createForColor(propSelectedTextColor));

		// Selection Color Color
		Property<Color> propSelectionColor = new Property<>(PROPERTY_BUNDLE.getString("selcolor"), textcomp,
				jtextcomp::setSelectionColor, jtextcomp::getSelectionColor);
		this.addToContent(SwingSettingFieldFactory.createForColor(propSelectionColor));
		
		// Caret Color
		Property<Color> propCaretColor = new Property<>(PROPERTY_BUNDLE.getString("caretcolor"), textcomp,
				jtextcomp::setCaretColor, jtextcomp::getCaretColor);
		this.addToContent(SwingSettingFieldFactory.createForColor(propCaretColor));

		// Margin
		Property<Insets> propMargin = new Property<>(PROPERTY_BUNDLE.getString("margin"), textcomp,
				jtextcomp::setMargin, jtextcomp::getMargin);
		this.addToContent(SwingSettingFieldFactory.createForInsets(propMargin));

		// Editable
		Property<Boolean> propEditable = new Property<>(PROPERTY_BUNDLE.getString("editable"), textcomp,
				jtextcomp::setEditable, jtextcomp::isEditable);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propEditable));
		
	}

}
