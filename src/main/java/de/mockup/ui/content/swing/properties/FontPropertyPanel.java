package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel displaying information for fonts
 */
class FontPropertyPanel extends AbstractPropertyPanel {

	public FontPropertyPanel(final ContentComponent<? extends JComponent> comp) {
		super(PROPERTY_BUNDLE.getString("font"));

		JComponent jcomp = comp.getContent();

		Property<Font> prop = new Property<>(PROPERTY_BUNDLE.getString("font"), comp, jcomp::setFont, jcomp::getFont);
		GridPane content = SwingSettingFieldFactory.createForFont(prop);
		this.addToContent(content);

	}

}
