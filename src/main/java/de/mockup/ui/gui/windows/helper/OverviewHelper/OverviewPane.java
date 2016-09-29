package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;

/**
 * ScrollPane which shows a view overview of the project.
 */
public class OverviewPane extends ScrollPane {

	private final TilePane tilePane;

	public OverviewPane(){
		tilePane=new TilePane(Orientation.HORIZONTAL);

		tilePane.getStyleClass().add("tilepane");

		tilePane.setHgap(50);
		tilePane.setVgap(50);

		tilePane.setVisible(true);

		this.setContent(tilePane);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);

		this.setVisible(true);
	}

	/**
	 * called to load the GridItems with current snapshot and title of the representative View
	 * @param views List of ContentViews of the current project
	 */
	public void loadTheGrid(ArrayList<ContentView> views ){
		int col = (int)(WorkingSurface.get().getWidth()/(200.0+tilePane.getHgap()));

		tilePane.getChildren().clear();
		tilePane.setPrefColumns(col);

		for(int i=0; i< views.size();i++){
			tilePane.getChildren().add(i,new ViewItem(views.get(i)));
		}
		tilePane.getChildren().add(new NewViewItem());

		deselectAll();
	}

	/**
	 * Method to deselect all GridItems
	 */
	public void deselectAll(){
		for (int i=0;i<tilePane.getChildren().size();i++){
			((GridItem)(tilePane.getChildren().get(i))).deselect();
		}
	}
}
