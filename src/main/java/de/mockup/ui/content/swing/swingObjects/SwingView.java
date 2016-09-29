package de.mockup.ui.content.swing.swingObjects;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.content.swing.properties.SwingProperties;
import de.mockup.ui.content.swing.properties.SwingPropertyPanelFactory;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a view on the <code>WorkingSurface</code>.
 */
public class SwingView extends ContentView<JPanel> {

	private String title;

	public SwingView(BaseComponentModel model) {
		super(new JPanel(), "", model);

		getContent().setName(getDescription());
		getContent().setLayout(null);
		getContent().setBorder(BorderFactory.createEtchedBorder());
	}

	@Override
	final public synchronized void addChild(ContentComponent<JPanel> component) {
		super.addChild(component);
		getContent().add(component.getContent());
		getContent().repaint();
	}

	@Override
	final public synchronized void removeChild(ContentComponent<JPanel> c) {
		super.removeChild(c);
		getContent().remove(c.getContent());
		getContent().repaint();
	}

	@Override
	final public String getDescription() {
		return getTitle();
	}

	@Override
	final protected List<AbstractPropertyPanel> getPanelConfig() {
		return SwingPropertyPanelFactory.createJPanelPropertyPanel(this);
	}

	final public String getType() {
		return ModelTypes.Container.VIEW;
	}

	@Override
	final public String getTitle() {
		return title;
	}

	@Override
	final public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getIdentifier() {
		return getContent().getName();
	}

	@Override
	public final synchronized void setBounds(Rectangle r) {
		super.setBounds(r);
		getContent().setBounds(getBounds());
	}

	@Override
	public final synchronized void setBounds(Point p, Dimension d) {
		super.setBounds(p, d);
		getContent().setBounds(getBounds());
	}

	@Override
	public final void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		getContent().setBounds(getBounds());
	}

	@Override
	public final void setLocation(Point p) {
		super.setLocation(p);
		getContent().setLocation(getLocation());
	}

	@Override
	public final void setLocation(int x, int y) {
		super.setLocation(x, y);
		getContent().setLocation(getLocation());
	}

	@Override
	public final void setSize(Dimension d) {
		super.setSize(d);
		getContent().setSize(getSize());
	}

	@Override
	public final void setSize(int width, int height) {
		super.setSize(width, height);
		getContent().setSize(getSize());
	}

	@Override
	public List<String> getStringKeys() {
		return new ArrayList<>();
	}

	@Override
	public List<Property<?>> getProperties() {
		ArrayList<Property<?>> result = new ArrayList<>();
		SwingProperties.addJPanelProperties(this, result);
		return result;
	}
}
