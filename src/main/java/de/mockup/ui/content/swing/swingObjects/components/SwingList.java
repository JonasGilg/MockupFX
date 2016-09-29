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

public class SwingList<E> extends SwingComponent<JList<E>> {


	public SwingList(BaseComponentModel model) {
		super(new JList<>(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.LIST;
	}

	@Override
	public String getDescription() {
		return "List";
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
	
	@Override public List<String> getStringKeys() {
		ArrayList<String> result = new ArrayList<>();
		for(int i=0; i < getContent().getModel().getSize(); i++){
		     result.add(getIdentifier() + "_" + "listElem" + i);
		}
		return result;
	}
}