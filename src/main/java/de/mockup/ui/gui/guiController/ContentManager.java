package de.mockup.ui.gui.guiController;

import de.mockup.system.model.components.ChildModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.ContextMenu;
import de.mockup.ui.gui.windows.helper.TabPaneHelper.ViewTab;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides methods for handling input on the {@link WorkingSurface} and {@link Utilities} class for Components.
 */
public class ContentManager {

	/**
     * The {@link ContentComponent} that was pressed on.
     */
    private static ContentComponent dragComp;

    //Local location at which the ContentComponent was dragged.
    private static int draggedAtX;
	private static int draggedAtY;

    //How far from the right/bottom edge of the ContentComponent the mouse was pressed.
	private static int fromRight = 0;
	private static int fromBottom = 0;

    /**
     * The width of the drag zones in pixel. (How precise the user has to be to rezise.)
     */
    private static final int RESIZE_ZONE_WIDTH = 4;

    //Zones determine where the ContentComponent can be resized.
	private static Rectangle topResizeZone;
	private static Rectangle rightResizeZone;
	private static Rectangle bottomResizeZone;
	private static Rectangle leftResizeZone;

    //If the ContentComponent was dragged inside the corresponding DragZone.
	private static boolean isResizedTop;
	private static boolean isResizedRight;
	private static boolean isResizedBottom;
	private static boolean isResizedLeft;

	/**
     * Saves the starting location for the {@link javax.swing.undo.UndoManager} when the mouse is pressed.
     */
    private static LocationDetails oldLocation;

	/**
     * If the user is marking via box selection.
     */
    private static boolean marking;

	/**
     * Saves the last shown {@link ContextMenu} to close it on the next click.
     */
    private static ContextMenu lastShown = null;

	public static void onMouseClick(MouseEvent event, ViewTab view) {
        Point p = Utilities.getPointInTab(event, view);
        ContentComponent clickedOn = Utilities.getDeepestComponentAt(view.getView(), p.x, p.y);

		if (clickedOn != null) {

            //If a ContextMenu is already displayed hide it.
			if (lastShown != null) {
				lastShown.hide();
			}

			if (event.getButton() == MouseButton.PRIMARY) {

			} else if (event.getButton() == MouseButton.SECONDARY) {
                //Show ContextMenu on right click.
				clickedOn.getContextMenu().show(view.getContent(), event.getScreenX(), event.getScreenY());
				lastShown = clickedOn.getContextMenu();
			} else if (event.getButton() == MouseButton.MIDDLE) {

			}
		}
	}

	public static void onMousePress(MouseEvent event, ViewTab view) {
        Point p = Utilities.getPointInTab(event, view);
        ContentComponent component = Utilities.getDeepestComponentAt(view.getView(), p.x, p.y);

        if (component != null) {
			Utilities.convertPointFromView(p, component);

			draggedAtX = (int) p.getX();
			draggedAtY = (int) p.getY();

			dragComp = component;

			if (event.isAltDown() && component instanceof ContentContainer) {
				marking = true;
				SelectionManager.setMarking(true);
			} else {
                //Save starting location for UndoManager
				oldLocation = new LocationDetails(component.getBounds(), component.getParent());

				fromRight = (int) (component.getWidth() - p.getX());
				fromBottom = (int) (component.getHeight() - p.getY());

				setResizeZones(component.getBounds());
				calculateResizeSide();
			}
		}
	}

	/**
     * Calculates if the drag was inside resize zones.
     */
    private static void calculateResizeSide() {
		isResizedTop = topResizeZone.contains(draggedAtX, draggedAtY);
		isResizedRight = rightResizeZone.contains(draggedAtX, draggedAtY);
		isResizedBottom = bottomResizeZone.contains(draggedAtX, draggedAtY);
		isResizedLeft = leftResizeZone.contains(draggedAtX, draggedAtY);
	}

	/**
     * Calculates the resize zones for the {@link Rectangle} <code>r</code>.
     * @param r the bounds of a {@link ContentComponent}
     */
    private static void setResizeZones(Rectangle r) {
		topResizeZone = new Rectangle(0, 0, (int) r.getWidth(), RESIZE_ZONE_WIDTH);
		rightResizeZone = new Rectangle((int) (r.getWidth() - RESIZE_ZONE_WIDTH), 0, RESIZE_ZONE_WIDTH, (int) r.getHeight());
		bottomResizeZone = new Rectangle(0, (int) r.getHeight() - RESIZE_ZONE_WIDTH, (int) r.getWidth(), RESIZE_ZONE_WIDTH);
		leftResizeZone = new Rectangle(0, 0, RESIZE_ZONE_WIDTH, (int) r.getHeight());
	}

	public static void onMouseRelease(MouseEvent event, ViewTab view) {
        Point p = Utilities.getPointInTab(event, view);
        ContentComponent component = Utilities.getDeepestComponentAt(view.getView(), p.x, p.y);

		if (component != null) {
			view.getContent().setCursor(Cursor.DEFAULT);

            //New location for the UndoManager
			LocationDetails newLocation = new LocationDetails(component.getBounds(), component.getParent());
			new UndoRedo<LocationDetails>("location", component,
					location -> {
						location.getParent().addChild(component);
						component.setBounds(location.getBounds());
					}, oldLocation, newLocation);
		}
		if (marking) {
			SelectionManager.setMarking(false);
			marking = false;
            WorkingSurface.get().repaintOverlay();
		} else {
			SelectionManager.setSingleSelection(dragComp);
		}
	}

	public static void onMouseDrag(MouseEvent event, ViewTab view) {
		if (dragComp != null) {
            Point p = Utilities.getPointInTab(event, view);
            Utilities.convertPointFromView(p, dragComp);

			if (marking) {
                //When the user makes a box selection. Calculate the selection box.
				Rectangle selection = new Rectangle(new Point(draggedAtX + view.getView().getX(), draggedAtY + view.getView().getY()),
						new Dimension(p.x - draggedAtX, p.y - draggedAtY));

				if (selection.getWidth() < 0) {
					selection.setBounds((int) (selection.getX() + selection.getWidth()), (int) selection.getY(),
							(int) -selection.getWidth(), (int) selection.getHeight());
				}

				if (selection.getHeight() < 0) {
					selection.setBounds((int) selection.getX(), (int) (selection.getY() + selection.getHeight()),
							(int) selection.getWidth(), (int) -selection.getHeight());
				}

				if (dragComp instanceof ContentContainer) {
					ContentContainer c = (ContentContainer) dragComp;
					ContentComponent[] children = c.getChildren();
					ArrayList<ContentComponent> selectedChildren = new ArrayList<>();

					for (ContentComponent child : children) {
						if (selection.intersects(child.getBounds())) {
							selectedChildren.add(child);
						}
					}
					SelectionManager.setMultiSelection(selectedChildren, Utilities.convertRectangle(dragComp, selection, view.getView()));
				}
			} else {

                //Start resizing.
				if (isResizedTop) {
					if (dragComp.getHeight() - p.getY() > RESIZE_ZONE_WIDTH * 2 && dragComp.getY() + p.getY() > 0) {
						dragComp.setBounds(dragComp.getX(), dragComp.getY() + (int) p.getY(), dragComp.getWidth(),
								(int) (dragComp.getHeight() - p.getY()));
					}
				}

				if (isResizedRight) {
					if (dragComp.getParent() != null) {
						if (p.getX() - fromRight > RESIZE_ZONE_WIDTH * 2 &&
								dragComp.getX() + p.getX() - fromRight < dragComp.getParent().getWidth()) {
							dragComp.setSize((int) (p.getX()) - fromRight, dragComp.getHeight());
						}
					} else if (dragComp instanceof ContentView) {
						dragComp.setSize((int) (p.getX()) - fromRight, dragComp.getHeight());
					}
				}

				if (isResizedBottom) {
					if (dragComp.getParent() != null) {
						if (p.getY() - fromBottom > RESIZE_ZONE_WIDTH * 2 &&
								dragComp.getY() + p.getY() - fromBottom < dragComp.getParent().getHeight()) {
							dragComp.setSize(dragComp.getWidth(), (int) p.getY() - fromBottom);
						}
					} else if (dragComp instanceof ContentView) {
						dragComp.setSize(dragComp.getWidth(), (int) p.getY() - fromBottom);
					}
				}

				if (isResizedLeft) {
					if (dragComp.getWidth() - p.getX() > RESIZE_ZONE_WIDTH * 2 && dragComp.getX() + p.getX() > 0) {
						dragComp.setBounds(dragComp.getX() + (int) p.getX(), dragComp.getY(), dragComp.getWidth() - (int) (p.getX()), dragComp.getHeight());
					}
				}
                //End resizing.

				if (!(isResizedTop || isResizedRight || isResizedBottom || isResizedLeft)) {
                    //Start translation.
					if (SelectionManager.getSingleSelection() != null) {
						setLocation(dragComp, p);
						SelectionManager.setSingleSelection(dragComp);
						view.getImageView().setCursor(Cursor.MOVE);
						if (!(dragComp instanceof ContentView)) {
							linkBreak(p, dragComp);
						}
					} else if (SelectionManager.getMultiSelection() != null) {
						view.getImageView().setCursor(Cursor.MOVE);
						for (ContentComponent mc : SelectionManager.getMultiSelection()) {
							setLocation(mc, p);
						}
					} else {
						SelectionManager.setSingleSelection(dragComp);
						view.getImageView().setCursor(Cursor.DEFAULT);
					}
                    //End translation.
				} else {
					SelectionManager.setSingleSelection(dragComp);
				}
				dragComp.notifyObservers("property.bounds");
			}
		}
	}

	/**
     * Sets the location of {@link ContentComponent} <code>c</code> to {@link Point} <code>p</code> under consideration
     * of grid snapping.
     * @param c the {@link ContentComponent} to be moved
     * @param p the {@link Point} to move to
     */
    private static void setLocation(ContentComponent c, Point p) {
		int x = c.getX() + (int) p.getX() - draggedAtX;
		int y = c.getY() + (int) p.getY() - draggedAtY;
		if (ProjectController.snapEnable) {
			x = x / 10 * 10;
			y = y / 10 * 10;
		}
		c.setLocation(x, y);
	}

	/**
     * Checks if the {@link ContentComponent} <code>c</code> was dragged outside its <code>parents</code> bounds or inside
     * another {@link ContentContainer} and moves it correspondingly.
     * @param p the {@link Point} the {@link ContentComponent} was moved to
     * @param c the moved {@link ContentComponent}
     */
    private static void linkBreak(Point p, ContentComponent c) {
		ContentContainer oldParent = c.getParent();
		ContentContainer newParent = null;
		if (p.x < 0 || p.y < 0 || p.x > oldParent.getWidth() || p.y > oldParent.getHeight()) {
			newParent = oldParent.getParent();
			if (!(oldParent instanceof ContentView)) {
				put(c, oldParent, newParent);
			}
		} else {
			p = Utilities.convertPoint(c, p, oldParent);
			for (ContentComponent child : oldParent.getChildren()) {
				if (child.getBounds().contains(p) && child != c && child instanceof ContentContainer) {
					newParent = (ContentContainer) child;
				}
			}
			if (newParent != null && !newParent.equals(c)) {
				put(c, oldParent, newParent);
			}
		}
	}

	/**
     * Changes the <code>parent</code> of the {@link ContentComponent} <code>c</code>.
     * @param c the {@link ContentComponent}
     * @param oldParent of the {@link ContentComponent} <code>c</code>
     * @param newParent of the {@link ContentComponent} <code>c</code>
     */
    private static synchronized void put(ContentComponent c, ContentContainer oldParent, ContentContainer newParent) {
		if (newParent != null && !oldParent.equals(newParent)) {
			if (Arrays.asList(oldParent.getChildren()).contains(newParent)) {
				c.setLocation(c.getX() - newParent.getX(), c.getY() - newParent.getY());
			} else {
				c.setLocation(c.getX() + oldParent.getX(), c.getY() + oldParent.getY());
			}

			newParent.addChild(c);

            //TODO parent changing of models should be done in lower layers (maybe implement in ContentComponent/ContentContainer?)
			GuiToControllerFacade.changeParent((ContainerModel) newParent.getComponentModel(),
					(ChildModel) c.getComponentModel());
			fitInParent(c, newParent);
		}
	}

	/**
     * Resizes the {@link ContentComponent} <code>c</code> to fit inside its <code>parent</code>.
     * @param c {@link ContentComponent}
     * @param parent {@link ContentContainer}
     */
    private static void fitInParent(ContentComponent c, ContentContainer parent) {
		if (c.getX() < 0) {
			c.setLocation(1, c.getY());
		}
		if (c.getY() < 0) {
			c.setLocation(c.getX(), 1);
		}
		if (c.getWidth() > parent.getWidth()) {
			c.setSize(parent.getWidth() - 2, c.getHeight());
		}
		if (c.getHeight() > parent.getHeight()) {
			c.setSize(c.getWidth(), parent.getHeight() - 2);
		}
		if (c.getX() + c.getWidth() > parent.getWidth()) {
			c.setLocation(parent.getWidth() - c.getWidth() - 1, c.getY());
		}
		if (c.getY() + c.getHeight() > parent.getHeight()) {
			c.setLocation(c.getX(), parent.getHeight() - c.getHeight() - 1);
		}
	}


	public static void onMouseMove(MouseEvent event, ViewTab view) {
        Point p = Utilities.getPointInTab(event, view);
        ContentComponent c = ContentManager.Utilities.getDeepestComponentAt(view.getView(), p.x, p.y);
		Utilities.convertPointFromView(p, c);

		if (c != null) {
            //Checks if mouse is inside resize zones and sets cursor correspondingly.
			setResizeZones(c.getBounds());
			if (topResizeZone.contains(p)) {
				if (rightResizeZone.contains(p)) {
					view.getImageView().setCursor(Cursor.NE_RESIZE);
				} else if (leftResizeZone.contains(p)) {
					view.getImageView().setCursor(Cursor.NW_RESIZE);
				} else {
					view.getImageView().setCursor(Cursor.N_RESIZE);
				}
			} else if (rightResizeZone.contains(p)) {
				if (bottomResizeZone.contains(p)) {
					view.getImageView().setCursor(Cursor.SE_RESIZE);
				} else {
					view.getImageView().setCursor(Cursor.E_RESIZE);
				}
			} else if (bottomResizeZone.contains(p)) {
				if (leftResizeZone.contains(p)) {
					view.getImageView().setCursor(Cursor.SW_RESIZE);
				} else {
					view.getImageView().setCursor(Cursor.S_RESIZE);
				}
			} else if (leftResizeZone.contains(p)) {
				view.getImageView().setCursor(Cursor.W_RESIZE);
			} else {
				view.getImageView().setCursor(Cursor.DEFAULT);
			}
		} else {
			view.getImageView().setCursor(Cursor.DEFAULT);
		}
		event.consume();
	}

	/**
	 * Provides a variety an methods to help with <code>ContentComponents</code>.
	 */
	public static class Utilities {

		/**
         * Calculates the true location of a point inside the {@link ContentView}.
         * @param event the {@link MouseEvent}
         * @param view the {@link ViewTab} that contains the {@link ContentView}
         * @return the true location without {@link ContentView} location and without zoom factor
         */
        protected static Point getPointInTab(MouseEvent event, ViewTab view) {
            return new Point((int) (event.getX() / view.getZoomFactor()) - view.getView().getX(), (int) (event.getY() / view.getZoomFactor()) - view.getView().getY());
        }

		/**
		 * Returns the first <code>ContentView</code> ancestor of <code>c</code>, or
		 * {@code null} if <code>c</code> is not contained inside a <code>ContentView</code>.
		 *
		 * @param c <code>ContentComponent</code> to get <code>ContentView</code> ancestor
		 *          of.
		 * @return the first <code>ContentView</code> ancestor of <code>c</code>, or
		 * {@code null} if <code>c</code> is not contained inside a
		 * <code>ContentView</code>.
		 */
		public static ContentView getViewAncestor(ContentComponent c) {
			for (ContentContainer p = c.getParent(); p != null; p = p.getParent()) {
				if (p instanceof ContentView) {
					return (ContentView) p;
				}
			}
			return null;
		}

		/**
		 * Converts the location <code>x</code> <code>y</code> to the
		 * parents coordinate system, returning the location.
		 */
		public static Point convertViewLocationToParent(ContentContainer parent, int x, int y) {
			for (ContentContainer p = parent; p != null; p = p.getParent()) {
				if (p instanceof ContentView) {
					Point point = new Point(x, y);

					convertPointFromView(point, parent);
					return point;
				}
			}
			throw new Error("convertScreenLocationToParent: no ContentView ancestor");
		}

		/**
		 * Convert a <code>aPoint</code> in <code>source</code> coordinate system to
		 * <code>destination</code> coordinate system.
		 * If <code>source</code> is {@code null}, <code>aPoint</code> is assumed to be in <code>destination</code>'s
		 * root component coordinate system.
		 * If <code>destination</code> is {@code null}, <code>aPoint</code> will be converted to <code>source</code>'s
		 * root component coordinate system.
		 * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>aPoint</code>
		 * without any conversion.
		 */
		public static Point convertPoint(ContentComponent source, Point aPoint, ContentComponent destination) {
			Point p;

			if (source == null && destination == null)
				return aPoint;
			if (source == null) {
				source = getViewAncestor(destination);
				if (source == null)
					throw new Error("Source component not connected to component tree hierarchy");
			}
			p = new Point(aPoint);
			convertPointToView(p, source);
			if (destination == null) {
				destination = getViewAncestor(source);
				if (destination == null)
					throw new Error("Destination component not connected to component tree hierarchy");
			}
			convertPointFromView(p, destination);
			return p;
		}

		/**
		 * Convert the point <code>(x,y)</code> in <code>source</code> coordinate system to
		 * <code>destination</code> coordinate system.
		 * If <code>source</code> is {@code null}, <code>(x,y)</code> is assumed to be in <code>destination</code>'s
		 * root component coordinate system.
		 * If <code>destination</code> is {@code null}, <code>(x,y)</code> will be converted to <code>source</code>'s
		 * root component coordinate system.
		 * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>(x,y)</code>
		 * without any conversion.
		 */
		public static Point convertPoint(ContentComponent source, int x, int y, ContentComponent destination) {
			Point point = new Point(x, y);
			return convertPoint(source, point, destination);
		}

		/**
		 * Convert the rectangle <code>aRectangle</code> in <code>source</code> coordinate system to
		 * <code>destination</code> coordinate system.
		 * If <code>source</code> is {@code null}, <code>aRectangle</code> is assumed to be in <code>destination</code>'s
		 * root component coordinate system.
		 * If <code>destination</code> is {@code null}, <code>aRectangle</code> will be converted to <code>source</code>'s
		 * root component coordinate system.
		 * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>aRectangle</code>
		 * without any conversion.
		 */
		public static Rectangle convertRectangle(ContentComponent source, Rectangle aRectangle, ContentComponent destination) {
			Point point = new Point(aRectangle.x, aRectangle.y);
			point = convertPoint(source, point, destination);
			return new Rectangle(point.x, point.y, aRectangle.width, aRectangle.height);
		}

		/**
		 * Returns the deepest visible descendent Component of <code>parent</code>
		 * that contains the location <code>x</code>, <code>y</code>.
		 * If <code>parent</code> does not contain the specified location,
		 * then <code>null</code> is returned.  If <code>parent</code> is not a
		 * container, or none of <code>parent</code>'s descendents
		 * contain the specified location, <code>parent</code> is returned.
		 *
		 * @param parent the root container to begin the search
		 * @param x      the x target location
		 * @param y      the y target location
		 */
		public static ContentComponent getDeepestComponentAt(ContentContainer parent, int x, int y) {
			if (!parent.contains(new Point(x, y))) {
				return null;
			}
			ContentComponent components[] = parent.getChildren();
			for (ContentComponent comp : components) {
				if (comp != null) {
					Point loc = new Point(x - comp.getX(), y - comp.getY());
					if (comp instanceof ContentContainer) {
						comp = getDeepestComponentAt((ContentContainer) comp, loc.x, loc.y);
					} else if (!comp.contains(loc)) {
						comp = null;
					}
					if (comp != null) {
						return comp;
					}
				}
			}
			return parent;
		}


		/**
		 * Convert a point from a component's coordinate system to
		 * view coordinates.
		 *
		 * @param p a Point object (converted to the new coordinate system)
		 * @param c a ContentComponent object
		 */
		public static void convertPointToView(Point p, ContentComponent c) {
			while (!(c == null || c instanceof ContentView)) {
				p.x += c.getX();
				p.y += c.getY();

				c = c.getParent();
			}
		}

		/**
		 * Convert a point from view coordinates to a component's
		 * coordinate system
		 *
		 * @param p a Point object (converted to the new coordinate system)
		 * @param c a ContentComponent object
		 */
		public static void convertPointFromView(Point p, ContentComponent c) {
			while (c != null && !(c instanceof ContentView)) {
				p.x -= c.getX();
				p.y -= c.getY();

				c = c.getParent();
			}
		}
	}
}

/**
 * Saves the parent and bounds inside that parent.
 */
class LocationDetails {
	private final Rectangle bounds;
	private final ContentContainer parent;

	public LocationDetails(Rectangle bounds, ContentContainer parent) {
		this.bounds = bounds;
		this.parent = parent;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public ContentContainer getParent() {
		return parent;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof LocationDetails)) {
			return false;
		}

		LocationDetails other = (LocationDetails) o;
		if (this.getParent() == null || other.getParent() == null) {
			return true;
		}

		return this.getBounds().equals(other.getBounds()) && this.getParent().equals(other.getParent());
	}
}
