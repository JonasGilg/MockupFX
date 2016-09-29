package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for displaying information such as color and other graphical options
 */
class RenderingPropertyPanel extends AbstractPropertyPanel {

	public RenderingPropertyPanel(final ContentComponent<? extends JComponent> comp) {
		super(PROPERTY_BUNDLE.getString("colorsrendering"));

		JComponent jcomp = comp.getContent();

		// Background & Foreground
		Property<Color> propBGColor = new Property<>(PROPERTY_BUNDLE.getString("bgcolor"), comp, jcomp::setBackground,
				jcomp::getBackground);
		this.addToContent(SwingSettingFieldFactory.createForColor(propBGColor));

		Property<Color> propFGColor = new Property<>(PROPERTY_BUNDLE.getString("fgcolor"), comp, jcomp::setForeground,
				jcomp::getForeground);
		this.addToContent(SwingSettingFieldFactory.createForColor(propFGColor));

/*
		// border
		Property<Border> propBorder = new Property<>("Border", jcomp, jcomp::setBorder, jcomp::getBorder);
		VBox BorderPanel = new VBox();
		Label borderLabel = new Label(propBorder.getVarName() + ": ");
		BorderPanel.getChildren().add(borderLabel);
		// TODO border chooser dialog erstellen
		this.addToContent(BorderPanel);
*/
		// doublebuffer
		Property<Boolean> propDBuff = new Property<>(PROPERTY_BUNDLE.getString("dbbuffer"), comp, jcomp::setDoubleBuffered,
				jcomp::isDoubleBuffered);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propDBuff));

		// opaque
		Property<Boolean> propOpaque = new Property<>(PROPERTY_BUNDLE.getString("opaque"), comp, jcomp::setOpaque, jcomp::isOpaque);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propOpaque));

		// ignorerepaint
		Property<Boolean> propIgnoreRepaint = new Property<>(PROPERTY_BUNDLE.getString("igrepaint"), comp, jcomp::setIgnoreRepaint,
				jcomp::getIgnoreRepaint);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propIgnoreRepaint));
	}

}
