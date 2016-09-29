package de.mockup.ui.gui.guiController;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.StylesheetModel;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.controller.logic.Management;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.HierarchyTree;
import de.mockup.ui.gui.windows.MainFrame;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.TabPaneHelper.ViewTab;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Provides method for drops on the <code>WorkingSurface</code>.
 */
public class DropController {

	/**
	 * Tries to create a component of the specified type at the <code>Point</code> p inside the given viewTab.
	 * @param p location of the drop
	 * @param type type of dropped object
	 * @param viewTab on which drop occurred
	 */
	public static void tryDrop(Point p, String type, ViewTab viewTab) {
		ContentView view = WorkingSurface.get().getSelectedView();
		ContentComponent droppedOn = ContentManager.Utilities.getDeepestComponentAt(view, p.x, p.y);
		if (!(droppedOn instanceof ContentContainer)) {
			droppedOn = droppedOn != null ? droppedOn.getParent() : null;
		}

		ContentContainer container = (ContentContainer) droppedOn;
		ContainerModel parentModel = (ContainerModel) container.getComponentModel();
		try {
			BaseComponentModel model;
			if (type.startsWith(ModelTypes.TEMPLATE_PREFIX)) {
				model = Management.createComponentFromTemplate(parentModel, type);
			} else {
				model = GuiToControllerFacade.addComponent(parentModel, type);
			}
			ContentComponent component = ModelManager.get().convert(model);

            //TODO a lot of code here should go into factory methods/lower layers!

			component.addObserver(viewTab);
			ContentManager.Utilities.convertPointFromView(p, droppedOn);
			container.addChild(component);
			component.setLocation(p);
			component.setSize(100, 50);
			/* STYLESHEET */
            // Get the chosen default parameters from the stylesheet for the component
            StylesheetModel stylesheetModel = ProjectController.getStylesheet();
            if (stylesheetModel != null) {
                stylesheetModel.applyToComponent(component);
            }
			/* STYLESHEET END */
            GuiToControllerFacade.changeAction(new AddUndoRedo(container, component, p));
			container.notifyObservers();
			component.notifyObservers();
			HierarchyTree.get().add(component);
			SelectionManager.setSingleSelection(component);
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
}

/**
 * UndoableEdit for drop action.
 */
class AddUndoRedo extends AbstractUndoableEdit {

	private final ContentContainer parent;
	private final ContentComponent child;
	private final Point p;

	public AddUndoRedo(ContentContainer parent, ContentComponent child, Point p) {
		this.parent = parent;
		this.child = child;
		this.p = p;
		MainFrame.get().setUndoRedoButtonState();
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		ProjectController.deleteComponent(child);
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		parent.addChild(child);
		child.setLocation(p);
	}
}
