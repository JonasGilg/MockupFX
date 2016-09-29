package de.mockup.ui.gui.windows;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.guiController.WindowManager;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.windows.helper.OverviewHelper.PropertyPane;
import de.mockup.ui.gui.windows.helper.OverviewHelper.ViewItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This Window contains all information to a given selected element on the WorkingSurface.
 * Because there should only exist one instance, this Window is created in WindowManager, so it is a singleton.
 * Preferable Position: On the right side, next to {@link WorkingSurface}.
 */
public class PropertyWindow extends TitlePane {

	//the reference to the MComponent, which will be displayed (ComponentOfInterest)
	private static ContentComponent<?> CoI = null;

	//list components that represents the propertyList
	private static ArrayList<Pane> listItems = null;

	private ArrayList<AbstractPropertyPanel> propertyPanelList;

	/**
	 * stackPane to toggle between content properties and overview properties
	 */
	private final StackPane backgroundStack;

	/**
	 * contains the name of the selected CoI and the listPanel.
	 */
	private final BorderPane mainPanel;
	/**
	 * placeholder for view overview panes
	 */
	private final PropertyPane overviewPane;

	/**
	 * the panel which contains all Properties in the propertyList. It has a GridLayout with 2 columns and unlimited rows.
	 * In the first column is a JLabel with Property.varName.
	 * In the second column is either a already premade Property.settingField (which are defined in Property) or a custom made settingsField.
	 * optional: Since the list of Properties could grow very long, this JPanel should be made or embedded into a JScrollPane.
	 */
	private final VBox listPanel;

	/**
	 * used to hold the {@link VBox listPanel}.
	 */
	private final ScrollPane scrollpane;
	private static Label componentName;

	/**
	 * standard constructor that first uses the init-function of DockableDialog and then its own custimInitiate().
	 */
	public PropertyWindow() {
		super(ResourceBundle.getBundle("properties.PropertyBundle").getString("properties"));

		listItems = new ArrayList<>();
		componentName = new Label();

		backgroundStack = new StackPane();
		mainPanel = new BorderPane();
		overviewPane = new PropertyPane();

		//###############################CONTENT PROPERTY GUI##########################
		listPanel = new VBox();
		scrollpane = new ScrollPane();
		scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollpane.setContent(listPanel);
		propertyPanelList = new ArrayList<>();

		//###############################OVERVIEW PROPERTY GUI##########################
		overviewPane.toBack();
		overviewPane.setVisible(true);
		mainPanel.toFront();
		backgroundStack.getChildren().addAll(overviewPane, mainPanel);
		setContent(backgroundStack);

		addBasicComponents();
	}

	/**
	 * Convenience method to get the Instance of this window
	 *
	 * @return instance of this window
	 */
	public static synchronized PropertyWindow get() {
		return WindowManager.get().getWindow(PropertyWindow.class);
	}

	/**
	 * adds all non-dynamic components to the window
	 */
	private void addBasicComponents() {
		//add the name of the Component on top, just below the titlebar
		StackPane namepane = new StackPane();
		namepane.getChildren().add(componentName);
		mainPanel.setTop(namepane);
		mainPanel.setCenter(scrollpane);
	}

	private void setPropertyPanelList(ArrayList<AbstractPropertyPanel> list) {
		propertyPanelList = list;
	}

	private void addPropertyPanelsToWindow() {
		removeListItems();
		//adds new PropertyPanels from the PropertyPanelList
		componentName.setText(CoI.getClass().getSimpleName());
		for (final AbstractPropertyPanel p : propertyPanelList) {
			listPanel.getChildren().add(p);
			listItems.add(p);
		}
	}

	/**
	 * deletes the items from the current listItems, so items of the new CoI can be shown
	 */
	private void removeListItems() {
		for (final Pane comp : listItems) {
			listPanel.getChildren().remove(comp);
		}
		listPanel.getChildren().removeAll();
		listItems.clear();
		listItems = new ArrayList<>();
		componentName.setText(null);
	}

	/**
	 * sets the ComponentOfInterest and updates the displayed elements
	 *
	 * @param coi new ComponentOfInterest
	 */
	public static void setComponentOfInterest(ContentComponent<?> coi) {
		if (CoI == null || CoI != coi) {
			//coi.removeAllObservers();
			CoI = coi;
			if (CoI == null) {
				PropertyWindow.get().removeListItems();
				return;
			}

			updateContents();
		}
	}

	/**
	 * gives access to the Component of Interest
	 *
	 * @return current Component of Interest
	 */
	public static ContentComponent<?> getComponentofInterest() {
		return CoI;
	}

	/**
	 * set of functions, that set up and update the PropertyWindow
	 */
	private static void updateContents() {
		PropertyWindow it = PropertyWindow.get();

		it.setPropertyPanelList((ArrayList<AbstractPropertyPanel>) CoI.getPropertyPanels());
		it.addPropertyPanelsToWindow();
	}

	//overviewProperty controlling methods

	public void toggleOverview(boolean state) {
		//state = overview currently shown? yes/no
		if (state) {
			overviewPane.toBack();
			overviewPane.setVisible(false);
			mainPanel.setVisible(true);
			mainPanel.toFront();
		} else {
			overviewPane.loadStartPage();
			mainPanel.toBack();
			mainPanel.setVisible(false);

			overviewPane.toFront();
			overviewPane.setVisible(true);
		}

	}
    public void loadOverViewProjectProperties(){
        overviewPane.loadStartPage();
    }

	public void loadOverviewProperties(ViewItem view) {
		overviewPane.loadView(view);
	}

}
