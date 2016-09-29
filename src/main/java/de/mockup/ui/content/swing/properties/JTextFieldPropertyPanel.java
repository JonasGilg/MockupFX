package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JTextFields
 */
class JTextFieldPropertyPanel extends AbstractPropertyPanel {

	public JTextFieldPropertyPanel(final SwingComponent<? extends JTextField> txtfield) {
		super("JTextField " + PROPERTY_BUNDLE.getString("properties"));

		JTextField jtxtfield = txtfield.getContent();

		// Columns
		Property<Integer> propColumns = new Property<>(PROPERTY_BUNDLE.getString("columns"), txtfield,
				jtxtfield::setColumns, jtxtfield::getColumns);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propColumns));

		// Horizontal Alignment TODO spezialfall
		Property<Integer> propHAlign = new Property<>(PROPERTY_BUNDLE.getString("alignH"), txtfield,
				jtxtfield::setHorizontalAlignment, jtxtfield::getHorizontalAlignment);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propHAlign));

		// Scroll Offset
		Property<Integer> propScrollOffset = new Property<>(PROPERTY_BUNDLE.getString("scrolloffset"), txtfield,
				jtxtfield::setScrollOffset, jtxtfield::getScrollOffset);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propScrollOffset));

	}
}
