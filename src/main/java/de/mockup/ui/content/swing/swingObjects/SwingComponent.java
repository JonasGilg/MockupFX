package de.mockup.ui.content.swing.swingObjects;

import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.ContentComponent;

import javax.swing.*;
import java.awt.*;

/**
 * The base class for every Swing leaf component that will be displayed on the <code>WorkingSurface</code>.
 *
 * @param <T> The JComponent that is being represented by this component.
 *            (For example: <code>JButton</code>, <code>JLabel</code>)
 */
public abstract class SwingComponent<T extends JComponent> extends ContentComponent<T> {

	protected SwingComponent(T content, BaseComponentModel model) {
		super(content, model);
		getContent().setName(getDescription() + " " + model.getId());
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
	public String getIdentifier() {
		return getContent().getName();
	}
}
