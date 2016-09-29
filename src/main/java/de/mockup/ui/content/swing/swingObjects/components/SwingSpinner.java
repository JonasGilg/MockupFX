package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingSpinner extends SwingComponent<JSpinner> {

	public SwingSpinner(BaseComponentModel model) {
		super(new JSpinner(), model);

		getContent().addMouseWheelListener(e -> {
			if (e.getWheelRotation() < 0) {
				getContent().setValue(getContent().getModel().getNextValue());
			} else {
				getContent().setValue(getContent().getModel().getPreviousValue());
			}
		});
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.SPINNER;
	}

	@Override
	public String getDescription() {
		return "Spinner";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJSpinnerProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJSpinnerPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys() {
		return new ArrayList<>();
	}
}