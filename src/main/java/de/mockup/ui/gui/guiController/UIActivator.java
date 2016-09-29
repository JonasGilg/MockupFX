package de.mockup.ui.gui.guiController;

import de.mockup.system.Activator;
import de.mockup.ui.gui.windows.HierarchyTree;
import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.Toolbar;
import de.mockup.ui.gui.windows.WorkingSurface;

import java.util.ResourceBundle;

/**
 * Registeres the BindingFactories and initialize the System context
 */
public class UIActivator {

	public void start() {
		final ResourceBundle TOOLBAR_BUNDLE = ResourceBundle.getBundle("properties.ToolbarBundle");
		//Initialize the System Services
		Activator ac = new Activator();
		ac.start();

		//Instantiate Windows
		WindowManager windowManager = WindowManager.get();
		windowManager.register(Toolbar.class, new Toolbar());
		windowManager.register(WorkingSurface.class, new WorkingSurface());
		windowManager.register(PropertyWindow.class, new PropertyWindow());
		String name = TOOLBAR_BUNDLE.getString("hiararchytree");
		windowManager.register(HierarchyTree.class, new HierarchyTree(name));
	}

}
