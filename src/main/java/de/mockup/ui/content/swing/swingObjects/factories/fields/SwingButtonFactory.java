package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.model.components.buttons.ButtonModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingButton;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

import javax.swing.*;
import java.awt.*;

/**
 * SwingComponentFactoryBase to bind SwingButton and ButtonModel.
 */
public class SwingButtonFactory extends SwingComponentFactoryBase<SwingButton, ButtonModel> {

	@Override
	public SwingButton createComponent(ButtonModel model) {
		return new SwingButton(model);
	}

	@Override
	public void writeToModel(SwingButton component, ButtonModel model) {
		writePropertiesToModel(component, model);
	}

	@Override
	public void readFromModel(ButtonModel model, SwingButton component) {
		readPropertiesFromModel(model, component);

		//Ugly, but necessary to get the gradient painted, when the color is default
		if (component.getContent().getBackground().equals(UIManager.get("Button.background"))) {
			component.getContent().setBackground((Color) UIManager.get("Button.background"));
		}
	}

}
