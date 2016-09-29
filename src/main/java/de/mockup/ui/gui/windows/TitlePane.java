package de.mockup.ui.gui.windows;

import de.mockup.ui.gui.globalListener.GlobalListenerManager;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.util.ResourceBundle;

/**
 * A Pane that contains a titlebar and a pop-out-button.
 */
public class TitlePane extends BorderPane {

	private Parent parent;
	private Node content;

	private static final ResourceBundle BASIC_BUNDLE = ResourceBundle.getBundle("properties.BasicBundle");

	public TitlePane(String title, Node content) {
		this(title);
		setContent(content);
	}

	TitlePane(String title) {
		super();

		BorderPane titlebar = new BorderPane();
		Label titelLabel = new Label(title);

		titlebar.setLeft(titelLabel);

		setTop(titlebar);

		if(GlobalListenerManager.isGlobalMouseListenerActive()) {
			Button popOutButton = new Button(BASIC_BUNDLE.getString("popOut"));
			titlebar.setRight(popOutButton);
			popOutButton.setOnAction(e -> {
				getChildren().remove(content);

				DragDropWindow tmp = new DragDropWindow(title, (Parent) content);
				tmp.setWidth(getWidth());
				tmp.setHeight(getHeight());

				if (parent instanceof DynamicSplitPane) {
					((DynamicSplitPane) parent).remove(this);
					parent = null;
				}

				tmp.show();
			});
		}
	}

	/**
	 * Sets the content of the Pane.
	 * @param content
	 */
	void setContent(Node content) {
		this.content = content;
		setCenter(content);
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
