package de.mockup.ui.content.swing.swingObjects.container;

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

public class SwingBox extends SwingComponent<Box> {

	public SwingBox(int axis, BaseComponentModel model) {
		super(new Box(axis), model);
	}

	public SwingBox(BaseComponentModel model) {
		this(0, model);
	}

	@Override
	public String getType() {
		return ModelTypes.Container.CONTAINER;
	}

	@Override
	public String getDescription() {
		return "Box";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addBasicProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(SwingPropertyPanelFactory.createJComponentPropertyPanel(this));
		return result;
	}
	
	@Override public List<String> getStringKeys(){
		return new ArrayList<>();
	}
}