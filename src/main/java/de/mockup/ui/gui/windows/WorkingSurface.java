package de.mockup.ui.gui.windows;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.guiController.SelectionManager;
import de.mockup.ui.gui.guiController.UserActionController;
import de.mockup.ui.gui.guiController.WindowManager;
import de.mockup.ui.gui.windows.helper.OverviewHelper.OverviewButton;
import de.mockup.ui.gui.windows.helper.OverviewHelper.OverviewPane;
import de.mockup.ui.gui.windows.helper.TabPaneHelper.PlusTab;
import de.mockup.ui.gui.windows.helper.TabPaneHelper.ViewTab;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 * working surface, where the project content is shown graphically and where you edit your project content
 */
public class WorkingSurface extends TitlePane implements Serializable {

    private final TabPane tabPane;
    private final OverviewPane overviewPane;

    private final PlusTab plusTab;
    private final OverviewButton overviewButton;

    private ArrayList<ContentView> views;
    private final Scene scene;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("properties.WorkingSurfaceBundle");
    private ArrayList<ViewTab> viewTabs;

    public WorkingSurface() {
        super(RESOURCE_BUNDLE.getString("workingSurface"));
        //lists
        views = new ArrayList<>();
        viewTabs = new ArrayList<>();
        //layered panes on rootPane
        tabPane = new TabPane();
        overviewPane = new OverviewPane();
        overviewButton = new OverviewButton();
        //tabPane config
        plusTab = new PlusTab();
        tabPane.getTabs().add(plusTab);
        tabPane.getStylesheets().add("stylesheets/tabs.css");
        //overviewPane config
        overviewPane.getStylesheets().add("stylesheets/overview.css");
        overviewPane.prefWidthProperty().bind(this.widthProperty());
        overviewPane.prefHeightProperty().bind(this.heightProperty());
        //rootPane configuration
        StackPane rootPane = new StackPane();
        rootPane.setAlignment(Pos.TOP_LEFT);
        rootPane.getChildren().addAll(overviewPane, tabPane, overviewButton);
        showOverview(false);
        scene = getScene();
        setContent(rootPane);

        //Action config
        final KeyCodeCombination copyCode = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        final KeyCodeCombination pasteCode = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        final KeyCodeCombination cutCode = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        setOnKeyPressed(event -> {
            if (copyCode.match(event)) {
                UserActionController.copy();
            }
            if (pasteCode.match(event)) {
                try {
                    Robot clickbot = new Robot();

                    if (SelectionManager.getSingleSelection() != null) {
                        clickbot.mousePress(InputEvent.BUTTON1_MASK);
                        clickbot.mouseRelease(InputEvent.BUTTON1_MASK);
                        //Point newPoint = new Point((int)(p.getX()+WorkingSurface.get().getTabCoordinates().getX()),(int)(p.getY()+WorkingSurface.get().getTabCoordinates().getY()));

                        //UserActionController.paste(SelectionManager.getSingleSelection(), p);
                    }
                } catch (AWTException ignored) {
                }
            }

            if (cutCode.match(event)) {
                UserActionController.cut();
            }
        });


    }

    /**
     * Convenience method to get the Instance of this window
     *
     * @return instance of this window
     */
    public static synchronized WorkingSurface get() {
        return WindowManager.get().getWindow(WorkingSurface.class);
    }

	/**
     * helping method to show the ToggleButton on the WorkingSurface. Called when a project is loaded onto the WorkingSurface
     * @param b has to be true
     */
    public void enableOverview(boolean b) {
        if (b) {
            overviewButton.setVisible(true);
        }
    }

	/**
     * Helpermethod to toggle between TabPane and ViewOverview
     * @param overviewShown <code>true</code> shows Overview; <code>false</code> shows TabPane
     */
    public void toggleOverview(boolean overviewShown) {
        if (!overviewShown) {
            showOverview(true);
        } else {
            showOverview(false);
        }
    }

	/**
     * loads the View Overview with all current project views
     */
    public void loadOverViewGrid() {
        overviewPane.loadTheGrid(views);
    }

    private void showOverview(boolean on) {
        if (on) {
            overviewPane.loadTheGrid(views);
            overviewPane.toFront();
            overviewPane.setVisible(true);
            tabPane.toBack();
            tabPane.setVisible(false);
            overviewButton.toFront();
        } else {

            tabPane.toFront();

            tabPane.setVisible(true);
            overviewPane.toBack();
            overviewPane.setVisible(false);
            overviewButton.toFront();
            refreshTabs();
        }
    }

    /**
     * refreshes the Names of the Tabs
     */
    private void refreshTabs() {
        //tabPane.getTabs().stream().filter(tab -> !tab.equals(plusTab)).forEach(tab -> ((ViewTab) tab).refresh());
        viewTabs.forEach(ViewTab::refresh);
    }

    /**
     * selects the ContentView in the WorkingSurface.TabPane
     *
     * @param cView to be selected
     */
    public void selectView(ContentView cView) {
        //TODO Das TabPane enthält u.U. den Tab nicht mehr, den es öffnen soll
        int index;
        for (ViewTab vTab : viewTabs) {
            if (vTab.getView().equals(cView)) {
                if (tabPane.getTabs().contains(vTab)) {
                    index = tabPane.getTabs().indexOf(vTab);
                    refreshTabs();
                    this.setSelectedTab(index);
                } else {
                    index = viewTabs.indexOf(vTab);
                    tabPane.getTabs().add(index, viewTabs.get(index));
                    refreshTabs();
                    this.setSelectedTab(index);
                }
            }
        }


        //this.setSelectedTab(tabPane.getTabs().indexOf(cView));
    }

    public Point getTabCoordinates() {
        Point2D stageCoord = new Point2D(getScene().getWindow().getX(), getScene().getWindow().getY());
        Point2D sceneCoord = new Point2D(scene.getX() + stageCoord.getX(), scene.getY() + stageCoord.getY());
        Point2D localCoord = tabPane.getSelectionModel().getSelectedItem().getContent().localToScene(0.0, 0.0);
        return new Point((int) (sceneCoord.getX() + localCoord.getX()), (int) (sceneCoord.getY() + localCoord.getY()));
    }

    /**
     * private method to add a new ViewTab on correct position to the TabPane and viewTabs-ArrayList
     *
     * @param viewTab viewtab to add
     */
    private void addNewTab(ViewTab viewTab) {
        viewTab.setOnClosed(event -> {
            if (tabPane.getTabs().size() == 2) {
                event.consume();
            }
        });
        //add viewTab to ViewTab list
        viewTabs.add(viewTab);
        //add viewTab to correct position in TabPane
        tabPane.getTabs().remove(plusTab);
        tabPane.getTabs().add(viewTab);
        tabPane.getSelectionModel().selectLast();
        tabPane.getTabs().add(plusTab);

    }

    /**
     * adds a new View to the WorkingSurface
     *
     * @param newView new view to add.
     */
    public void addView(ContentView newView) {
        views.add(newView);
        ViewTab tab = new ViewTab(newView);

        @SuppressWarnings("unchecked")
        ArrayList<ContentComponent> components = newView.getDescendants();

        for(ContentComponent component : components) {
            component.addObserver(tab);
            component.addObserver(newView);
        }

        addNewTab(tab);
        SelectionManager.setSingleSelection(newView);
        HierarchyTree.get().addView(newView); //(throws nullpointer)
    }

    /**
     * sets the WorkindSurface into EditMode or UseMode
     *
     * @param editMode true = editMode
     */
    void setEditMode(boolean editMode) {
        if (editMode) {
            for (int i = 0; i < tabPane.getTabs().size() - 1; i++) {
                ViewTab tab = (ViewTab) tabPane.getTabs().get(i);
                tab.setEditMode();
            }
        } else {
            for (int i = 0; i < tabPane.getTabs().size() - 1; i++) {
                ViewTab tab = (ViewTab) tabPane.getTabs().get(i);
                tab.setTestMode();
            }
            SelectionManager.deselect();
        }
    }

    /**
     * only call from ProjectController.deleteView(int index)
     */

    public void deleteView(ContentView view) {
        int index = views.indexOf(view);
        HierarchyTree.get().removeView(views.get(index));

        for (Tab tab : tabPane.getTabs()) {
            if (!tab.equals(plusTab)) {
                if (((ViewTab) tab).getView().equals(view)) {
                    tabPane.getTabs().remove(tab);
                    viewTabs.remove(tab);
                    break;
                }
            }
        }

        if (!views.remove(view)) System.out.println("remove failed");
    }

    /**
     * only call from ProjectController.deleteViews()
     */
    public void deleteViews() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(plusTab);
        views = new ArrayList<>();
        viewTabs = new ArrayList<>();
        //this.setTitle(RESOURCE_BUNDLE.getString("workingSurface"));
    }

    /**
     * @param index of Tab on TabPane
     * @return ViewNode of selected Tab
     */
    private Node getViewNode(int index) {
        if (!tabPane.getTabs().get(index).equals(plusTab)) {
            ViewTab tmpTab = (ViewTab) tabPane.getTabs().get(index);
            return tmpTab.getContentNode();
        }
        return null;
    }
    public ViewTab getSelectedTab(){
        return (ViewTab) tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex());
    }

	/**
     *
     * @param view of which you want to get the node it lays in
     * @return the ViewNode the <code>view</code> lays in
     */
    public Node getViewNode(ContentView view) {
        try {
            for (ViewTab tab : viewTabs) {
                if (tab.getView().equals(view)) {
                    return tab.getContentNode();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return ContentView of currently selected Tab
     */
    public ContentView getSelectedView() {
        ViewTab tmpTab = getSelectedTab();
        return tmpTab.getView();
    }

    /**
     * @return List of all Views
     */
    public ArrayList<ContentView> getViews() {
        return views;
    }

	/**
     *
     * @return the ViewNode in the currently selected Tab
     */
    public Node getSelectedViewNode() {
        return getViewNode(tabPane.getSelectionModel().getSelectedIndex());
    }

	/**
     * repaints the WorkingSurface overlay
     */
    public void repaintOverlay() {
        if (!tabPane.getSelectionModel().getSelectedItem().equals(plusTab)) {
            Pane pane = getOverlay();
            pane.getChildren().clear();


            if (SelectionManager.getSingleSelection() != null) {
                ContentComponent component = SelectionManager.getSingleSelection();
                addComponentOverlay(pane, component);
            }
            if (SelectionManager.getMultiSelection() != null) {
                for (ContentComponent mc : SelectionManager.getMultiSelection()) {
                    addComponentOverlay(pane, mc);
                }
            }
            if (SelectionManager.isMarking()) {
                try {
                    java.awt.Rectangle r = SelectionManager.getSelection();

                    javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle((int) r.getWidth(), (int) r.getHeight());
                    rectangle.relocate((int) r.getX(), (int) r.getY());
                    rectangle.setFill(javafx.scene.paint.Color.TRANSPARENT);
                    rectangle.setStroke(javafx.scene.paint.Color.GRAY);

                    pane.getChildren().add(rectangle);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private void addComponentOverlay(Pane pane, ContentComponent component) {
        try {
            ViewTab t = getSelectedTab();
            java.awt.Rectangle r = component.getBoundsOnView();
            r.setLocation((int) ((r.x + component.getView().getX()) * t.getZoomFactor()), (int) ((r.y + component.getView().getY()) * t.getZoomFactor()));

            javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle((int) (r.getWidth() * t.getZoomFactor() - 6), (int) (r.getHeight() * t.getZoomFactor() - 6));
            rectangle.relocate((int) r.getX() + 3, (int) r.getY() + 3);
            rectangle.setFill(javafx.scene.paint.Color.TRANSPARENT);
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);
            rectangle.setStrokeWidth(1);
            rectangle.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);

            pane.getChildren().add(rectangle);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

	/**
     *
     * @return the overlay of the WorkingSurface
     */
    public Pane getOverlay() {
        return getSelectedTab().getOverlay();
    }

    private void setSelectedTab(int index) {
        tabPane.getSelectionModel().select(index);
    }

	/**
     *
     * @return the view overview pane
     */
    public OverviewPane getOverviewPane() {
        return this.overviewPane;
    }
}
