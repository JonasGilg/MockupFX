package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.ui.gui.guiController.OverviewController;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.windows.PropertyWindow;
import javafx.scene.image.ImageView;

/**
 * Special GridItem which has only one function: Create a new view on the workingsurface.tabpane and switch to it.
 */
class NewViewItem extends GridItem {

	public NewViewItem(){
		super("add new view");
		this.setCenter(new ImageView("icons/plus_4.png"));
		this.setOnMouseClicked(e->{
			if(e.getClickCount()==1){
				select();
                PropertyWindow.get().loadOverViewProjectProperties();
			}
			if(e.getClickCount()==2) {
				ProjectController.addNewView();
				OverviewController.toggleOverview();
			}
		});
	}
}
