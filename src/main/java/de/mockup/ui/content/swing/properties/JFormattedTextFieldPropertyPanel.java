package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingFormattedTextField;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

class JFormattedTextFieldPropertyPanel extends AbstractPropertyPanel {

	public JFormattedTextFieldPropertyPanel(final SwingFormattedTextField formatfeld) {
		super("JFormattedTextField" + PROPERTY_BUNDLE.getString("properties"));
		//TODO

		/*
		void 	setDocument(Document doc)
		void 	setFocusLostBehavior(int behavior)
		void 	setFormatterFactory(JFormattedTextField.AbstractFormatterFactory tf)//optional
		*/
	}

}
