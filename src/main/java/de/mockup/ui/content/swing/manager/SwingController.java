package de.mockup.ui.content.swing.manager;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.swing.swingObjects.factories.SwingLabelFactory;
import de.mockup.ui.content.swing.swingObjects.factories.SwingTableFactory;
import de.mockup.ui.content.swing.swingObjects.factories.SwingTreeFactory;
import de.mockup.ui.content.swing.swingObjects.factories.container.SwingContainerFactory;
import de.mockup.ui.content.swing.swingObjects.factories.container.SwingViewFactory;
import de.mockup.ui.content.swing.swingObjects.factories.fields.*;
import de.mockup.ui.gui.guiController.SelectionManager;
import de.mockup.ui.gui.windows.HierarchyTree;

import javax.swing.*;
import java.awt.*;

public class SwingController {

	public static void init() {
		SwingLaFManager.initialize();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		ModelManager modelManager = ModelManager.get();

		modelManager.registerComponent(ModelTypes.Container.CONTAINER, new SwingContainerFactory());
		modelManager.registerComponent(ModelTypes.Container.VIEW, new SwingViewFactory());

		modelManager.registerComponent(ModelTypes.Button.BUTTON, new SwingButtonFactory());

		modelManager.registerComponent(ModelTypes.LABEL, new SwingLabelFactory());
		modelManager.registerComponent(ModelTypes.TABLE, new SwingTableFactory());
		modelManager.registerComponent(ModelTypes.TREE, new SwingTreeFactory());

		modelManager.registerComponent(ModelTypes.Fields.SLIDER, new SwingSliderFactory());
		modelManager.registerComponent(ModelTypes.Fields.SPINNER, new SwingSpinnerFactory());
		modelManager.registerComponent(ModelTypes.Fields.CHECKBOX, new SwingCheckBoxFactory());
		modelManager.registerComponent(ModelTypes.Fields.RADIO, new SwingRadioButtonFactory());
		modelManager.registerComponent(ModelTypes.Fields.TEXT_FIELD, new SwingTextFieldFactory());
		modelManager.registerComponent(ModelTypes.Fields.COMBO_BOX, new SwingComboBoxFactory());
	}

    @Deprecated //TODO de.mockup.ui.gui.guiController.ProjectController.deleteComponent should be used instead
	public static void deleteComponent(ContentComponent<JComponent> component) {
		SelectionManager.deselect();
		JComponent c = component.getContent();
		Container parent = c.getParent();
		parent.remove(c);
		parent.repaint();
		HierarchyTree.get().remove(component);
	}

	public static void addToParent(ContentComponent<JComponent> child, ContentContainer<JComponent> parent, Point p) {
		parent.addChild(child);
		child.getContent().setLocation(p);
	}
}
