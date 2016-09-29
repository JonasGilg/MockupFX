package de.mockup.ui.gui.windows.helper.OverviewHelper;

import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.guiController.OverviewController;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * GridItem that represents a View.
 * Has snapshot and title of represented View
 */
public class ViewItem extends GridItem {
	private final ContentView view;

	public ViewItem(ContentView view) {
		super(view.getTitle());
		this.view = view;
		Node node = WorkingSurface.get().getViewNode(view);

		//take snapshot of ContentView, not the whole ViewTab
		Rectangle rect = view.getBounds();
		SnapshotParameters param = new SnapshotParameters();
		param.setViewport(new Rectangle2D(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight()));
		Image snapshot = node.snapshot(param, null);
		this.setThumbnail(snapshot);
		this.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				OverviewController.loadView(this);
				select();
			}
			if (e.getClickCount() == 2) {
				OverviewController.toggleOverview();
				WorkingSurface.get().selectView(view);
			}
		});
	}

	/**
	 * Returns its ContentView
	 *
	 * @return ContentView
	 */
	public ContentView getContentView() {
		return view;
	}

}
