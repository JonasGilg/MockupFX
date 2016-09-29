package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingComboBox;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

class JComboBoxPropertyPanel extends AbstractPropertyPanel {

	public JComboBoxPropertyPanel(final SwingComboBox combo) {
		super("JComboBox" + PROPERTY_BUNDLE.getString("properties"));

		JComboBox jcombo = new JComboBox();

		//Editable
		Property<Boolean> propEditable = new Property<>(PROPERTY_BUNDLE.getString("editable"), combo,
				jcombo::setEditable, jcombo::isEditable);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propEditable));

		//Enabled
		Property<Boolean> propEnabled = new Property<>(PROPERTY_BUNDLE.getString("isEnabled"), combo,
				jcombo::setEnabled, jcombo::isEnabled);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propEnabled));

		//Maximum row count
		Property<Integer> propMaximumRowCount = new Property<>(PROPERTY_BUNDLE.getString("maxrowcount"), combo,
				jcombo::setMaximumRowCount, jcombo::getMaximumRowCount);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propMaximumRowCount));

		//Popup visible
		Property<Boolean> propPopupVisible = new Property<>(PROPERTY_BUNDLE.getString("popupvisible"), combo,
				jcombo::setPopupVisible, jcombo::isPopupVisible);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propPopupVisible));

	}

}
