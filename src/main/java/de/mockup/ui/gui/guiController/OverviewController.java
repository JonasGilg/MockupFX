package de.mockup.ui.gui.guiController;

import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.OverviewHelper.ViewItem;

public class OverviewController {
	private static boolean overviewShown = false;

    public static void toggleOverview() {
		if (!overviewShown) {
			WorkingSurface.get().toggleOverview(overviewShown);
			PropertyWindow.get().toggleOverview(overviewShown);
			overviewShown = true;
		} else {
			WorkingSurface.get().toggleOverview(overviewShown);
			PropertyWindow.get().toggleOverview(overviewShown);
			overviewShown = false;
		}
	}

	/**
	 * @param item the view to show in properties
	 */
	public static void loadView(ViewItem item) {
		PropertyWindow.get().loadOverviewProperties(item);
	}

}
