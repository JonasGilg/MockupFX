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

public class SwingComboBox<E> extends SwingComponent<JComboBox<E>> {

	public SwingComboBox(BaseComponentModel model) {
		super(new JComboBox<>(), model);
	}

	@Override
	public String getType() {
		return ModelTypes.Fields.COMBO_BOX;
	}

	@Override
	public String getDescription() {
		return "ComboBox";
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJComboBoxProperties(this, result);
		return result;
	}

	@Override
	protected ArrayList<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJComboBoxPropertyPanel(this);
	}
	
	@Override public List<String> getStringKeys() {
		ArrayList<String> result = new ArrayList<>();
		for(int i=0; i < getContent().getModel().getSize(); i++){
		     result.add(getIdentifier() + "_" + "comboboxElem" + i);
		}
		return result;
	}
}