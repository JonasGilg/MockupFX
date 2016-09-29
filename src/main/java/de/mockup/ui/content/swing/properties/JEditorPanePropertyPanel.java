package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingEditorPane;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.net.URL;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

class JEditorPanePropertyPanel extends AbstractPropertyPanel{

	public JEditorPanePropertyPanel(final SwingEditorPane pane) {
		super("JEditorPane" + PROPERTY_BUNDLE.getString("properties"));

		JEditorPane jpane = new JEditorPane();

		//Text
		Property<String> propText = new Property<>(PROPERTY_BUNDLE.getString("text"), pane,
				jpane::setText, jpane::getText);
		this.addToContent(SwingSettingFieldFactory.createForString(propText));
		
		//Page
		Property<URL> propPage = new Property<>(PROPERTY_BUNDLE.getString("page"), pane,
				input -> { try {
					jpane.setPage(input);
				} catch (Exception e) {
					e.printStackTrace();
				} }, jpane::getPage);
		this.addToContent(SwingSettingFieldFactory.createForURL(propPage));
	}

}
