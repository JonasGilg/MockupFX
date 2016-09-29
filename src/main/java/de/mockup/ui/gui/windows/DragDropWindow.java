package de.mockup.ui.gui.windows;

import de.mockup.ui.gui.globalListener.GlobalListenerManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import java.util.ResourceBundle;

import java.awt.*;

/**
 * A Window that can be dragged over the MainFrame to drop its content into the MainFrame.
 */
class DragDropWindow extends Stage {

	private static final ResourceBundle ERROR_BUNDLE = ResourceBundle.getBundle("properties.ErrorBundle");
	private final DragDropListener dragDropListener;
	private final Node content;
	private final Pane rootPane;
	private boolean cleaned = false;

	DragDropWindow(String title, Parent content) {
		super();

		setTitle(title);

            this.content = content;

            rootPane = new Pane();
            setScene(new Scene(rootPane));

            if (this.content != null)
                rootPane.getChildren().add(content);

            dragDropListener = new DragDropListener();

            if (GlobalListenerManager.isGlobalMouseListenerActive()) {
                GlobalScreen.addNativeMouseListener(dragDropListener);
                GlobalScreen.addNativeMouseMotionListener(dragDropListener);
            }
	}

	/**
	 * Removes the GlobalMouseListeners. Needs to be called before finalization.
	 */
	private void cleanUp() {
		if(GlobalListenerManager.isGlobalMouseListenerActive()) {
			try {
				GlobalScreen.removeNativeMouseListener(dragDropListener);
				GlobalScreen.removeNativeMouseMotionListener(dragDropListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cleaned = true;
	}

	@Override
	protected void finalize() throws Throwable {
		if(!cleaned) {
			System.err.println(ERROR_BUNDLE.getString("object") + ": " + getClass().getName() + ERROR_BUNDLE.getString("finalizeError"));
			cleanUp();
		}
		super.finalize();
	}

	/**
	 * A global MouseListener that listens for events, that regard the DragDropWindow.
	 */
	private class DragDropListener implements NativeMouseInputListener {

		private boolean pressedOnDecoration;
		private boolean canDrop;

		@Override
		public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
			Platform.runLater(() -> {
				canDrop = false;
				if (!pressedOnDecoration && isShowing()) {
					Point clickLocation = nativeMouseEvent.getPoint();
					Rectangle stageDecoration = new Rectangle((int) getX() + 5, (int) getY() + 5,
							(int) getWidth() - 10, (int) (getHeight() - getScene().getHeight()) - 5);

					if(pressedOnDecoration = stageDecoration.contains(clickLocation)) {
						MainFrame.get().startDrag();
					}
				}
			});
		}

		@Override
		public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
			Platform.runLater(() -> {
				if (pressedOnDecoration) {
					pressedOnDecoration = false;
					if(canDrop) {
						rootPane.getChildren().remove(content);
						MainFrame.get().dropOn(nativeMouseEvent.getPoint(), new TitlePane(getTitle(), content));
						cleanUp();
						close();
					} else {
						MainFrame.get().stopDrag();
					}
				}
			});
		}

		@Override
		public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
			Platform.runLater(() -> {
				if(pressedOnDecoration) {
					canDrop = MainFrame.get().hoverOver(nativeMouseEvent.getPoint());
				}
			});
		}

		/* ####################### UNUSED #######################*/

		@Override
		public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {}

		@Override
		public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {}
	}
}
