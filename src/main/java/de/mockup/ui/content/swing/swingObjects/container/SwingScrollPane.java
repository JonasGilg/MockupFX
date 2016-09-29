package de.mockup.ui.content.swing.swingObjects.container;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import javafx.scene.control.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingScrollPane extends SwingComponent<JScrollPane> {

	public SwingScrollPane(BaseComponentModel model) {
		super(new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), model);
		getContent().setOpaque(true);

		getContent().setBackground(Color.GREEN);
		getContent().setVisible(true);
		getContent().setFocusable(true);

		configureContextMenu();
	}

	/*public SwingScrollPane(JTable table) {
		super(table, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setOpaque(true);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		contextMenu = new ContextMenu(this);
		setFocusable(true);

		SwingContainerListener c = new SwingContainerListener();
		addMouseListener(c);
		addMouseMotionListener(c);
		addKeyListener(c);
		configureContextMenu();
	}*/

	@Override
	public void configureContextMenu() {
		MenuItem scrollpaneitem = new MenuItem("scrollpane");
		getContextMenu().addMenuItemAt(scrollpaneitem, 1);

	}

	@Override
	public String getDescription() {
		return "ScrollPane";
	}

	@Override
	public java.util.List<Property<?>> getProperties() {
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

	@Override
	public String getType() {
		return ModelTypes.Container.SCROLL_PANE;
	}
	
	@Override public List<String> getStringKeys(){
		return new ArrayList<>();
	}
}
