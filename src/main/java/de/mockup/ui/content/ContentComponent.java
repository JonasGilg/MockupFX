package de.mockup.ui.content;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.gui.guiController.ContentManager;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.HierarchyTree;
import de.mockup.ui.gui.windows.helper.ContextMenu;

/**
 * Represents a Component on the <code>WorkingSurface</code> it manages the location, dimension and <code>parent</code>
 * properties.
 * It also contains information about the context menu that is shown when right clicking and the
 * <code>PropertyPanels</code> that are displayed in the <code>PropertyWindow</code>.
 *
 * @param <T> The content that is being represented by this component.
 *           (For example: <code>JPanel</code>, <code>BorderPane</code>)
 */
public abstract class ContentComponent<T> extends Observable {

	protected static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("properties.ComponentBundle");

	private ContentContainer<T> parent;
	private final BaseComponentModel model;
	private final T content;

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;

	private String description;
	private final ContextMenu contextMenu;

	protected ContentComponent(T content, BaseComponentModel model) {
		this.model = model;
		this.content = content;
		this.contextMenu = new ContextMenu(this);
		this.parent = null;
		configureContextMenu();
		if (!(this instanceof ContentView)) {
			addObserver(HierarchyTree.get());
		}
		this.description = "Your description here";
	}

	/**
	 * @return the context menu that is being displayed on a right click
	 */
	final public ContextMenu getContextMenu() {
		return contextMenu;
	}

	/**
	 * @return the model that represents the properties of the component
	 */
	final public BaseComponentModel getComponentModel() {
		return model;
	}

	/**
	 * @return the project specific content
	 */
	final public T getContent() {
		return content;
	}

	@Override
	final public void notifyObservers() {
		setChanged();
		super.notifyObservers();
		updateModel();
	}

	@Override
	final public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
		updateModel();
	}

	/**
	 * Writes the updated properties to the model.
	 */
	private void updateModel() {
		getProperties().stream().filter(p -> p != null).forEach(p ->
				getComponentModel().setProperty(p.getVarName(), p.toConfig())
		);
	}

	/**
	 * Returns a list of all Properties of this component, which can be modified
	 *
	 * @return the list of properties of the ContentComponent
	 */
	public abstract List<Property<?>> getProperties();

	/**
	 * @return a unique identifier for this component
	 */
	public abstract String getIdentifier();


	/**
	 * Components without any key to assign values to should return an empty List.
	 *
	 * @return List of Key which will be mapped to values in a properties-file
	 */
	public abstract List<String> getStringKeys();

	/**
	 * @return a user specified description of the Object
	 */
	public String getDescription() {
		return description;
	}

	public List<AbstractPropertyPanel> getPropertyPanels() {
		return getPanelConfig();
	}

	/**
	 * @return the PropertyPanels which will be displayed in the PropertyWindow
	 */
	protected abstract List<AbstractPropertyPanel> getPanelConfig();

	final public ContentContainer<T> getParent() {
		return parent;
	}

	/**
	 * Changes the components parent.
	 * @param newParent
	 */
	void setParent(ContentContainer<T> newParent) {
		if (newParent != null && newParent != this.parent) {
			setChanged();
		}
		deleteObserver(getView());
		this.parent = newParent;
		if (this.parent != null && getView() != null) {
			addObserver(getView());
		}
	}

	/**
	 * @return the type of the StorageModel
	 */
	public abstract String getType();

	/**
	 * This method should configure the ContextMenu to its own needs
	 */
	protected void configureContextMenu() {
	}

	public final Rectangle getBoundsOnView() {
		return new Rectangle(getLocationOnView(), new Dimension(getWidth(), getHeight()));
	}

	private Point getLocationOnView() {
		Point p = new Point();
		ContentManager.Utilities.convertPointToView(p, this);
		return p;
	}

	public final Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public void setBounds(Point p, Dimension d) {
		setLocation(p);
		setSize(d);
	}

	public void setBounds(int x, int y, int width, int height) {
		setLocation(x, y);
		setSize(width, height);
	}

	public final Point getLocation() {
		return new Point(x, y);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	public void setLocation(int x, int y) {
		if (x != this.x || y != this.y) {
			setChanged();
		}

		if (x < 0) {
			x = 0;
		} else if (parent != null && x + width > parent.getWidth()) {
			x = parent.getWidth() - width;
		}

		if (y < 0) {
			y = 0;
		} else if (parent != null && y + height > parent.getHeight()) {
			y = parent.getHeight() - height;
		}

		this.x = x;
		this.y = y;
	}

	public final Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public void setSize(int width, int height) {
		if (width != this.width || height != this.height) {
			setChanged();
		}

		if (parent != null) {
			if (x + width > parent.getWidth()) {
				width = parent.getWidth() - x;
			}
			if (y + height > parent.getHeight()) {
				height = parent.getHeight() - y;
			}
		}

		this.width = width;
		this.height = height;
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public final int getWidth() {
		return width;
	}

	public final int getHeight() {
		return height;
	}

	public boolean contains(Point p) {
		Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
		return r.contains(p);
	}

	/**
	 * @return the view in which this component is located or null if the component has no parent
	 */
	public ContentView<T> getView() {
		if (this instanceof ContentView) {
			return (ContentView<T>) this;
		}

		if (getParent() == null) {
			return null;
		}
		return getParent().getView();
	}
}
