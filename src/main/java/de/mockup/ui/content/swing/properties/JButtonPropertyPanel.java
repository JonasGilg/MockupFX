package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingButton;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JButtons
 */
class JButtonPropertyPanel extends AbstractPropertyPanel{

	public JButtonPropertyPanel(final SwingButton comp) {
		super("JButton "+PROPERTY_BUNDLE.getString("properties"));

		JButton jcomp = new JButton();

		//DefaultCapable
		Property<Boolean> propDefCapable = new Property<>(PROPERTY_BUNDLE.getString("defaultcapable"), comp,
				jcomp::setDefaultCapable, jcomp::isDefaultCapable);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propDefCapable));
	}
}