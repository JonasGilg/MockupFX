package de.mockup.ui.gui.properties;

import de.mockup.ui.gui.windows.PropertyWindow;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

/**
 * Base class for all Propertypanels
 */
public abstract class AbstractPropertyPanel extends BorderPane {

	private TitledPane titlepane;
	private String panelname;
	private VBox contentPanel;

	/**
	 * Map that holds Class-Boolean value pairs which can be used to remember which type of {@link AbstractPropertyPanel}
	 * is expanded and which is not.
	 */
	protected static final HashMap<Class<? extends AbstractPropertyPanel>, Boolean> EXPAND_MAP;

	static {
		EXPAND_MAP = new HashMap<>();
	}

	/**
	 * constructs a PropertyPanel with its name in the TitlePane and the displayed content inside.
	 *
	 * @param panelname the name displayed at the top of this panel
	 */
	protected AbstractPropertyPanel(String panelname) {
		super();
		this.panelname = panelname;
		this.prefWidthProperty().bind(PropertyWindow.get().widthProperty());
		this.setMinWidth(PropertyWindow.get().getWidth());
		this.minWidthProperty().bind(PropertyWindow.get().widthProperty());
		optionalConstructor();
		createPanel();
	}

	/**
	 * template method for sub classes if they want to implement code that comes before
	 * createPanel() is called.
	 */
	protected void optionalConstructor() {
		//nothing to do here for super class 
	}

	/*the default look and behavior of a PropertyPanel*/
	private void createPanel() {
		this.contentPanel = new VBox();

		this.titlepane = new TitledPane(panelname, contentPanel);
		if (!EXPAND_MAP.containsKey(this.getClass())) {
			EXPAND_MAP.put(this.getClass(), false);
		}

		this.titlepane.setExpanded(EXPAND_MAP.get(this.getClass()));
		this.titlepane.expandedProperty().addListener((observable, oldValue, newValue) -> EXPAND_MAP.put(this.getClass(), newValue));

		this.setCenter(titlepane);
	}

	/**
	 * convenience method to expand the accordion
	 */
	protected void expand() {
		this.titlepane.setExpanded(true);
		EXPAND_MAP.put(this.getClass(), true);
	}

	/**
	 * convenience method to close the accordion
	 */
	protected void unexpand() {
		this.titlepane.setExpanded(false);
		EXPAND_MAP.put(this.getClass(), false);
	}

	/**
	 * Use this method to add new GUI elements to the contentPanel of PropertyPanel
	 *
	 * @param node new Node,which will be added to the accordions content pane
	 */
	protected void addToContent(Node node) {
		this.contentPanel.getChildren().add(node);
	}

	public VBox getContentPanel() {
		return this.contentPanel;
	}
}