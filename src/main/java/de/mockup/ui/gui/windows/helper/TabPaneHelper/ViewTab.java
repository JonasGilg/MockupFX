package de.mockup.ui.gui.windows.helper.TabPaneHelper;

import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.guiController.ContentManager;
import de.mockup.ui.gui.guiController.DropController;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * {@link Tab} to show a {@link ContentView} on the {@link WorkingSurface}. It has two possible representations of the
 * content:
 * <ul>
 *     1. {@link ImageView}: An image of the content is used while editing. <br>
 *     2. {@link Node}: The real content of the view. It is used while in test mode.
 * </ul>
 */
public class ViewTab extends Tab implements Observer {

	/**
     * Is used to draw the dashed outline over the selected {@link de.mockup.ui.content.ContentComponent}.
     */
    private final Pane overlayNode;

	/**
	 * Represents the content inside the {@link ViewTab}. It is retrieved via the {@link GuiToContentFacade}. <br><br>
     * <b>Example:</b>
     * <ul>
     *     Swing-Projects have a {@link javafx.embed.swing.SwingNode} <br>
     *     Web-Projects have a {@link javafx.scene.web.WebView}
     * </ul>
     */
    private final Node contentNode;

	/**
	 * A picture of the {@code contentNode}. It is used while in {@code editMode} to prevent the user of interaction
     * with the real content when editing.
     */
    private final ImageView imageView;

    private final AnchorPane rootPane;
    private ScrollPane scrollPane;

    private double zoomFactor = 1.0;

    private final ContentView view;

    private final EventHandler<MouseEvent> clickEvent = (event -> ContentManager.onMouseClick(event, ViewTab.this));
    private final EventHandler<MouseEvent> pressEvent = (event -> ContentManager.onMousePress(event, ViewTab.this));
    private final EventHandler<MouseEvent> releaseEvent = (event -> ContentManager.onMouseRelease(event, ViewTab.this));
    private final EventHandler<MouseEvent> dragEvent = (event -> ContentManager.onMouseDrag(event, ViewTab.this));
    private final EventHandler<MouseEvent> moveEvent = (event -> {
        ContentManager.onMouseMove(event, ViewTab.this);
        scrollPane.requestFocus();
    });

	public ViewTab(ContentView view) {
		setClosable(true);

        this.view = view;

        rootPane = new AnchorPane();
        scrollPane = new ScrollPane();
        contentNode = GuiToContentFacade.getViewNode(view);
        view.addObserver(this);
        if(view.getWidth() == 0 || view.getHeight() == 0) {
            view.setSize(200, 200);
        }

        imageView = new ImageView(contentNode.snapshot(null, null));

        imageView.setOnDragOver(event -> {
			if (event.getDragboard().hasString() && view.getBounds().contains(
					new Point(((int) (event.getX() / zoomFactor)), (int) (event.getY() / zoomFactor)))) {
				event.acceptTransferModes(TransferMode.COPY);
			}
			event.consume();
		});

        imageView.setOnDragDropped(event -> {
			if (event.getDragboard().hasString()) {
				DropController
						.tryDrop(new Point((int) (event.getX() / zoomFactor) - view.getX(),
								(int) (event.getY() / zoomFactor) - view.getY()), event.getDragboard().getString(),
								ViewTab.this);
				repaint();
				event.consume();
			}
		});

        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);
        AnchorPane.setBottomAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);

        AnchorPane.setTopAnchor(contentNode, 0.0);
        AnchorPane.setRightAnchor(contentNode, 0.0);
        AnchorPane.setBottomAnchor(contentNode, 0.0);
        AnchorPane.setLeftAnchor(contentNode, 0.0);

        rootPane.getChildren().add(imageView);

        overlayNode = new Pane();
        overlayNode.setPickOnBounds(false);
        overlayNode.setMouseTransparent(true);
        rootPane.getChildren().add(overlayNode);

        setContent(scrollPane);
        scrollPane.setContent(rootPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setText(view.getTitle());

        contentNode.setMouseTransparent(false);

		addMouseListeners();
	}

    public void refresh(){
        String newName= ((ViewModel) view.getComponentModel()).getTitle();
        this.setText(newName);
    }

    public void setEditMode() {
        rootPane.getChildren().remove(contentNode);
        rootPane.getChildren().add(imageView);
        rootPane.getChildren().add(overlayNode);
    }

    public void setTestMode() {
        rootPane.getChildren().remove(imageView);
        rootPane.getChildren().remove(overlayNode);
        rootPane.getChildren().add(contentNode);
    }

    public ContentView getView() {
        return view;
    }

    public Node getContentNode() {
        return contentNode;
    }

    public ImageView getImageView() {
        return imageView;
    }

	public String getViewName(){
		return this.getText();
	}

    public Pane getOverlay() {
        return overlayNode;
    }

    private void addMouseListeners() {
        imageView.setOnMouseClicked(clickEvent);
        imageView.setOnMousePressed(pressEvent);
        imageView.setOnMouseReleased(releaseEvent);
        imageView.setOnMouseDragged(dragEvent);
        imageView.setOnMouseMoved(moveEvent);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void zoomIn() {
        zoomFactor += 0.1;
        repaint();
    }

    public void zoomOut() {
        if(zoomFactor > 0.1) {
            zoomFactor -= 0.1;
            repaint();
        }
    }

    public void repaint() {
        imageView.setFitWidth((view.getX() + view.getWidth()) * zoomFactor);
        imageView.setFitHeight((view.getY() + view.getHeight()) * zoomFactor);
        imageView.setImage(contentNode.snapshot(null, null));
        WorkingSurface.get().repaintOverlay();
    }
}
