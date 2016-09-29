package de.mockup.ui.content.swing.swingObjects.container;


import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;

import javax.swing.*;
import java.awt.*;

/**
 * The base class for every Swing container (except SwingView).
 *
 * @param <T> The content that is being represented by this component.
 *           (For example: <code>JPanel</code>, <code>JScrollPane</code>)
 */
public abstract class SwingContainer<T extends JComponent> extends ContentContainer<T> {

	protected SwingContainer(T content, BaseComponentModel model) {
		super(content, model);
		getContent().setName(getDescription());
	}

	@Override
	public synchronized void addChild(ContentComponent<T> component) {
		super.addChild(component);
		getContent().add(component.getContent());
		getContent().repaint();
	}


	@Override
	final public synchronized void removeChild(ContentComponent<T> c) {
		super.removeChild(c);
		getContent().remove(c.getContent());
		getContent().repaint();
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
	public String getIdentifier(){
		return getContent().getName();
	}
}
