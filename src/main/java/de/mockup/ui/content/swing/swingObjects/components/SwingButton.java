package de.mockup.ui.content.swing.swingObjects.components;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.Application;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.content.functionallity.interfaces.Button;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.dialogs.ButtonFunctionalityDialog;
import javafx.scene.control.MenuItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SwingButton extends SwingComponent<JButton> implements Button {
	ContentView viewConnectedTo = null;

	public SwingButton(BaseComponentModel model) {
		super(new JButton(RESOURCE_BUNDLE.getString("new.button")), model);
		getContent().addActionListener(e -> {
			if (viewConnectedTo != null) WorkingSurface.get().selectView(viewConnectedTo);
		});

	}

	@Override
	public final void configureContextMenu() {
		MenuItem itemFunction = new MenuItem(RESOURCE_BUNDLE.getString("functionality"));
		itemFunction.setOnAction(event -> new ButtonFunctionalityDialog(Application.getPrimaryStage(), this));
		this.getContextMenu().addMenuItemAt(itemFunction, 1);
	}

	@Override
	public String getDescription() {
		return getContent().getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJButtonProperties(this, result);
		return result;
	}

	@Override
	protected List<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJButtonPropertyPanel(this);
	}

	public String getType() {
		return ModelTypes.Button.BUTTON;
	}

	@Override
	public List<String> getStringKeys() {
		List<String> result = new ArrayList<>();
		result.add(getIdentifier() + "_" + "buttontext");
		return result;
	}

	public void connectToView(ContentView view) {
		viewConnectedTo = view;
	}


}
