package de.mockup.ui.gui.windows;

import de.mockup.system.exceptions.SystemException;
import de.mockup.ui.Application;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.gui.actions.*;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.guiController.UserActionController;
import de.mockup.ui.gui.windows.dialogs.IconLibFrame;
import de.mockup.ui.gui.windows.dialogs.LookAndFeelChooser;
import de.mockup.ui.gui.windows.dialogs.ProjectStylesheetChooser;
import de.mockup.ui.gui.windows.dialogs.stringeditor.StringEditorController;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.ResourceBundle;

/**
 * This is the root of the UI.
 */
public class MainFrame extends BorderPane {

	private static final javafx.scene.paint.Color OCCUPIED_COLOR = javafx.scene.paint.Color.color(1.0, 0.0, 0.0, 0.5);
	private static final javafx.scene.paint.Color FREE_COLOR = javafx.scene.paint.Color.color(0.0, 1.0, 0.0, 0.5);
	private static MainFrame frame = null;
	private final ResourceBundle menuBundle;
    private final AnchorPane centerRoot;
    private SplitPane horizontal;
	private DynamicSplitPane left;
	private Rectangle leftTopRect;
	private Rectangle leftBottomRect;
	private boolean leftDropPossible;
	private DynamicSplitPane center;
	private Rectangle centerRect;
	private boolean centerDropPossible;
	private DynamicSplitPane right;
	private Rectangle rightTopRect;
	private Rectangle rightBottomRect;
	private boolean rightDropPossible;
	//Deklaration der Action Objekte
	private NewProjectAction newProject;
	private OpenProjectAction openProject;
	private ExportProjectAction exportProject;
	private SaveAsProjectAction saveAsProject;
	private Action saveProject;
	private Action exitProject;
	private Action undoAction;
	private Action redoAction;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private Action laFAction;
	private Action pOverviewAction;
	private Action stringEditorAction;
	private Action projectStylesheetAction;
	private Action chooseTemplatesAction;
	private Action iconLibAction;
	private Action storybookEditorAction;
	private MenuBar menu;
	private Menu file;
	private Menu edit;
	private Menu view;
	private Menu window;
	private CheckMenuItem newToolbar;
	private CheckMenuItem newWorkingSurface;
	private CheckMenuItem newPropertyWindow;
	private CheckMenuItem tree;
	private HBox buttonPanel;
	private CheckableAction editMode;
	//private EditAction editMode;

	private MainFrame() {
		super();

		menuBundle = ResourceBundle.getBundle("properties.MenuBundle");
		BorderPane top;
		setPrefHeight(50);
		setMinSize(400, 50);
		setMaxHeight(50);

		initActions();

		createMenu();
		createButtons();
		initDragOverlay();

		top = new BorderPane();
		top.setTop(menu);
		top.setBottom(buttonPanel);

		setTop(top);

		centerRoot = new AnchorPane();
		initSplitPanes();

		centerRoot.getChildren().add(horizontal);
		setCenter(centerRoot);

		setVisible(true);
	}

	public static synchronized MainFrame get() {
		if (frame == null) {
			frame = new MainFrame();
			return frame;
		} else {
			return frame;
		}
	}

	private void createMenu() {
		menu = new MenuBar();

		createFileMenu();
		createEditMenu();
		createViewMenu();
		createWindowMenu();

		menu.getMenus().add(file);
		menu.getMenus().add(edit);
		menu.getMenus().add(view);
		menu.getMenus().add(window);
	}

	private void initActions() {
		newProject = new NewProjectAction(menuBundle);
		openProject = new OpenProjectAction(menuBundle);
		exportProject = new ExportProjectAction(menuBundle);
		saveAsProject = new SaveAsProjectAction(menuBundle);
		saveProject = new Action(menuBundle.getString("save"),"icons/save.png", true, e -> ProjectController.saveProject());
		exitProject = new Action(menuBundle.getString("close"),"icons/closeprog.png",e -> fireEvent(new WindowEvent(Application.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST)));
		undoAction = new Action(menuBundle.getString("undo"),"icons/undo.png",true, e -> ProjectController.undoFunction());
		redoAction = new Action(menuBundle.getString("redo"), "icons/redo.png", true, e -> ProjectController.redoFunction());
		cutAction = new Action(menuBundle.getString("cut"), "icons/content-cut.png", true, e -> UserActionController.cut());
		copyAction = new Action(menuBundle.getString("copy"), "icons/content-copy.png", true, e -> UserActionController.copy());
		pasteAction = new Action(menuBundle.getString("paste"), "icons/content-paste.png", true, null);
		//paste.setOnAction(e -> UserActionController.paste(SelectionManager.getSingleSelection(), new Point(0, 0)));
		laFAction = new Action(menuBundle.getString("lookAndFeel"), "icons/laf.png", true, e-> new LookAndFeelChooser(Application.getPrimaryStage()));
		pOverviewAction = new Action(menuBundle.getString("projektOverview"), "icons/overview.png", true, e -> System.out.println("Ihre Implementierung hier!"));
        stringEditorAction = new Action(menuBundle.getString("stringEdit"), "icons/string-editor.png", true, e -> StringEditorController.getInstance().showView());
        iconLibAction = new Action(menuBundle.getString("iconLib"), "icons/iconlib.png", false, e -> {
            try {
                new IconLibFrame(Application.getPrimaryStage());
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        });
		projectStylesheetAction = new Action(menuBundle.getString("projectStyle"), "icons/projectstyle.png", true, e -> new ProjectStylesheetChooser(Application.getPrimaryStage()));
		chooseTemplatesAction = new Action(menuBundle.getString("templates"), "icons/templates.png", true, e -> System.out.println("Ihre Implementierung hier!"));
		storybookEditorAction = new Action(menuBundle.getString("storybook"),"icons/storybook.png", true, e -> System.out.println("Ihre Implementierung hier!"));
		editMode = new CheckableAction(menuBundle.getString("edit"),"icons/control.png",true,false,(observable, oldValue, newValue)-> WorkingSurface.get().setEditMode(!newValue));
	}

	private void createFileMenu() {
		file = new Menu(menuBundle.getString("file"));

		file.getItems().add(newProject.createMenuItem());
		file.getItems().add(openProject.createMenuItem());
		file.getItems().add(new SeparatorMenuItem());
		file.getItems().add(saveProject.createMenuItem());
		file.getItems().add(saveAsProject.createMenuItem());
		file.getItems().add(new SeparatorMenuItem());
		file.getItems().add(exportProject.createMenuItem());
		//file.getItems().add(exportProject.createMenuItem()); TODO: exportToPDF
		file.getItems().add(exitProject.createMenuItem());

	}


	private void createEditMenu() {
		edit = new Menu(menuBundle.getString("edit"));
		edit.getItems().add(undoAction.createMenuItem());
		edit.getItems().add(redoAction.createMenuItem());
		edit.getItems().add(new SeparatorMenuItem());
		edit.getItems().add(cutAction.createMenuItem());
		edit.getItems().add(copyAction.createMenuItem());
		edit.getItems().add(pasteAction.createMenuItem());
		edit.getItems().add(editMode.createMenuItem());
	}

	private void createViewMenu() {
		view = new Menu(menuBundle.getString("view"));
		view.getItems().add(laFAction.createMenuItem());
		view.getItems().add(pOverviewAction.createMenuItem());
		view.getItems().add(stringEditorAction.createMenuItem());
		view.getItems().add(iconLibAction.createMenuItem());
		view.getItems().add(projectStylesheetAction.createMenuItem());
		view.getItems().add(chooseTemplatesAction.createMenuItem());
		view.getItems().add(storybookEditorAction.createMenuItem());
	}

	private void createWindowMenu() {

		ImageView toolbarIcon;
		try {
			toolbarIcon = new ImageView("icons/toolbar.png");
		} catch (Exception e) {
			toolbarIcon = new ImageView("");
		}

		ImageView treeIcon;
		try {
			treeIcon = new ImageView("icons/hierarchytree.png");
		} catch (Exception e) {
			treeIcon = new ImageView("");
		}

		ImageView propertyIcon;
		try {
			propertyIcon = new ImageView("icons/settings.png");
		} catch (Exception e) {
			propertyIcon = new ImageView("");
		}

		ImageView workspaceIcon;
		try {
			workspaceIcon = new ImageView("icons/workingsurface.png");
		} catch (Exception e) {
			workspaceIcon = new ImageView("");
		}

		window = new Menu(menuBundle.getString("window"));

		newToolbar = new CheckMenuItem(menuBundle.getString("toolbar"), toolbarIcon);
		window.getItems().add(newToolbar);


		newWorkingSurface = new CheckMenuItem(menuBundle.getString("workingSurface"), workspaceIcon);
		window.getItems().add(newWorkingSurface);

		newWorkingSurface.setSelected(true);

		newPropertyWindow = new CheckMenuItem(menuBundle.getString("settings"), propertyIcon);
		window.getItems().add(newPropertyWindow);


		tree = new CheckMenuItem(menuBundle.getString("hierarchyTree"), treeIcon);
		window.getItems().add(tree);
	}

	void updateWindowCheckButtonStates() {
		newToolbar.setSelected(Toolbar.get().isVisible());
		newWorkingSurface.setSelected(WorkingSurface.get().isVisible());
		newPropertyWindow.setSelected(PropertyWindow.get().isVisible());
		tree.setSelected(HierarchyTree.get().isVisible());
	}

	private void createButtons() {
		buttonPanel = new HBox();

		buttonPanel.getChildren().add(newProject.createButton());
		buttonPanel.getChildren().add(openProject.createButton());
		buttonPanel.getChildren().add(saveProject.createButton());
		buttonPanel.getChildren().add(exportProject.createButton());
        buttonPanel.getChildren().add(new Separator(Orientation.VERTICAL));
		buttonPanel.getChildren().add(undoAction.createButton());
		buttonPanel.getChildren().add(redoAction.createButton());
		buttonPanel.getChildren().add(cutAction.createButton());
		buttonPanel.getChildren().add(copyAction.createButton());
		buttonPanel.getChildren().add(pasteAction.createButton());
        buttonPanel.getChildren().add(new Separator(Orientation.VERTICAL));
		buttonPanel.getChildren().add(laFAction.createButton());
		buttonPanel.getChildren().add(pOverviewAction.createButton());
		buttonPanel.getChildren().add(stringEditorAction.createButton());
		buttonPanel.getChildren().add(iconLibAction.createButton());
		buttonPanel.getChildren().add(projectStylesheetAction.createButton());
		buttonPanel.getChildren().add(chooseTemplatesAction.createButton());
		buttonPanel.getChildren().add(storybookEditorAction.createButton());
		//buttonPanel.getChildren().add(editMode.createButton());
		buttonPanel.getStyleClass().add("hbox");
	}

	public void setUndoRedoButtonState() {
		undoAction.setDisable(!GuiToControllerFacade.canUndo());
		redoAction.setDisable(!GuiToControllerFacade.canRedo());
	}

	public void setLaFButtonState(boolean b) {
		laFAction.setDisable(!b);
	}

	public void setProjectOverviewButtonState(boolean b) {
		pOverviewAction.setDisable(!b);
	}

	public void setStringEditorButtonState(boolean b) {
		stringEditorAction.setDisable(!b);
	}

	public void setProjectStyleSheetButtonState(boolean b) {
		projectStylesheetAction.setDisable(!b);
	}

	public void setTemplateButtonState(boolean b) {
		chooseTemplatesAction.setDisable(!b);
	}

	public void setIconLibraryButtonState(boolean b) {
		iconLibAction.setDisable(!b);
	}

	public void setStoryBookEditorButtonState(boolean b) {
		storybookEditorAction.setDisable(!b);
	}

	public void setSaveButtonState(boolean b) {
		saveProject.setDisable(!b);
		saveAsProject.setDisable(!b);
	}

	public void setExportButtonState(boolean b) {
		exportProject.setDisable(!b);
	}

	public void setCutButtonState(boolean b) {
		cutAction.setDisable(!b);
	}

	public void setCopyButtonState(boolean b) {
		copyAction.setDisable(!b);
	}

	public void setPasteButtonState(boolean b) {
		pasteAction.setDisable(!b);
	}

	public void setControlModeButtonState(boolean b) {
		editMode.setDisable(!b);
	}

	/**
	 * Initializes the <code>Rectangles</code> of the overlay.
	 */
	private void initDragOverlay() {
		leftTopRect = new Rectangle();
		leftTopRect.setStroke(Color.BLACK);

		leftBottomRect = new Rectangle();
		leftBottomRect.setStroke(Color.BLACK);

		centerRect = new Rectangle();
		centerRect.setStroke(Color.BLACK);

		rightTopRect = new Rectangle();
		rightTopRect.setStroke(Color.BLACK);

		rightBottomRect = new Rectangle();
		rightBottomRect.setStroke(Color.BLACK);
	}

	private void initSplitPanes() {
		horizontal = new SplitPane();
		AnchorPane.setBottomAnchor(horizontal, 0.0);
		AnchorPane.setTopAnchor(horizontal, 0.0);
		AnchorPane.setLeftAnchor(horizontal, 0.0);
		AnchorPane.setRightAnchor(horizontal, 0.0);

		left = new DynamicSplitPane(Orientation.VERTICAL);
		left.setPrefWidth(200);

		left.add(0, Toolbar.get());
		Toolbar.get().setParent(left);

		left.add(1, HierarchyTree.get());
		HierarchyTree.get().setParent(left);

		center = new DynamicSplitPane(Orientation.VERTICAL);
		center.setPrefWidth(800);
		center.setMinWidth(600);
		center.add(0, WorkingSurface.get());
		WorkingSurface.get().setParent(center);

		right = new DynamicSplitPane(Orientation.VERTICAL);
		right.setPrefWidth(200);
		right.add(0, PropertyWindow.get());
		PropertyWindow.get().setParent(right);

		horizontal.setOrientation(Orientation.HORIZONTAL);
		horizontal.getItems().add(0, left);
		left.setParent(horizontal);

		horizontal.getItems().add(1, center);
		center.setParent(horizontal);

		horizontal.getItems().add(2, right);
		right.setParent(horizontal);
	}

	private boolean setDropColors(DynamicSplitPane pane, Rectangle top, Rectangle bottom) {
		int size = pane.getItems().size();
		if(size >= 2) {
			top.setFill(OCCUPIED_COLOR);
			bottom.setFill(OCCUPIED_COLOR);
			return false;
		} else {
			top.setFill(FREE_COLOR);
			bottom.setFill(FREE_COLOR);
			return true;
		}
	}

	private void setBounds(Rectangle r, double x, double y, double w, double h) {
		r.setX(x);
		r.setY(y);
		r.setWidth(w);
		r.setHeight(h);
	}

	private boolean containsPoint(Rectangle r, Point p) {
		return r.localToScreen(r.getBoundsInLocal()).contains(p.getX(), p.getY());
	}

	void startDrag() {
		centerRoot.getChildren().addAll(leftTopRect, leftBottomRect, centerRect, rightTopRect, rightBottomRect);

		double height, halfHeight;
		height = centerRoot.getHeight();
		halfHeight = height / 2;

		double leftWidth, rightWidth, centerWidth;
		if(left.isAttached()) {
			leftWidth = left.getWidth();
		} else {
			leftWidth = centerRoot.getWidth() / 4;
		}

		if(right.isAttached()) {
			rightWidth = right.getWidth();
		} else {
			rightWidth = centerRoot.getWidth() / 4;
		}

		if(center.isAttached()) {
			centerWidth = centerRoot.getWidth() - leftWidth - rightWidth;
		} else {
			centerWidth = centerRoot.getWidth() / 2;
		}

		setBounds(leftTopRect, 0.0, 0.0, leftWidth, halfHeight);
		setBounds(leftBottomRect, 0.0, halfHeight, leftWidth, halfHeight);
		setBounds(centerRect, leftWidth, 0.0, centerWidth, height);
		setBounds(rightTopRect, leftWidth + centerWidth, 0.0, rightWidth, halfHeight);
		setBounds(rightBottomRect, leftWidth + centerWidth, halfHeight, rightWidth, halfHeight);

		leftDropPossible = setDropColors(left, leftTopRect, leftBottomRect);
		rightDropPossible = setDropColors(right, rightTopRect, rightBottomRect);

		int centerSize = center.getItems().size();
		if(centerSize >= 1) {
			centerRect.setFill(OCCUPIED_COLOR);
			centerDropPossible = false;
		} else {
			centerRect.setFill(FREE_COLOR);
			centerDropPossible = true;
		}
	}

	boolean hoverOver(Point p) {
		if (leftDropPossible && (containsPoint(leftTopRect, p) || containsPoint(leftBottomRect, p))) {
			return true;
		}

		return (centerDropPossible && containsPoint(centerRect, p))
				|| (rightDropPossible && (containsPoint(rightTopRect, p) || containsPoint(rightBottomRect, p)));

	}

	void dropOn(Point p, TitlePane d) {
		if(containsPoint(leftTopRect, p)) {
			if(!left.isAttached()) {
				horizontal.getItems().add(0, left);
				left.setParent(horizontal);
			}
			left.getItems().add(0, d);
			d.setParent(left);
		} else if(containsPoint(leftBottomRect, p)) {
			if(!left.isAttached()) {
				horizontal.getItems().add(0, left);
				left.setParent(horizontal);
			}
			if(left.getItems().size() < 1) {
				horizontal.getItems().add(0, left);
			} else {
				left.getItems().add(1, d);
			}
			d.setParent(left);
		} else if(containsPoint(centerRect, p)) {
			if(!center.isAttached()) {
				if(left.isAttached()) {
					horizontal.getItems().add(1, center);
				} else {
					horizontal.getItems().add(0, center);
				}
				center.setParent(horizontal);
			}
			center.getItems().add(d);
			d.setParent(center);
		} else if(containsPoint(rightTopRect, p)) {
			if(!right.isAttached()) {
				horizontal.getItems().add(right);
				right.setParent(horizontal);
			}
			right.getItems().add(0, d);
			d.setParent(right);
		} else if(containsPoint(rightBottomRect, p)) {
			if(!right.isAttached()) {
				horizontal.getItems().add(right);
				right.setParent(horizontal);
			}
			if(right.getItems().size() < 1) {
				right.getItems().add(0, d);
			} else {
				right.getItems().add(1, d);
			}
			d.setParent(right);
		}

		stopDrag();
	}

	/**
	 * Removes the DragOverlay.
	 */
	void stopDrag() {
		centerRoot.getChildren().removeAll(leftTopRect, leftBottomRect, centerRect, rightTopRect, rightBottomRect);
	}

	/**
     * Adds the listeners to the scene that are accessible throughout the whole application.
     */
    public void addApplicationListeners() {
        if(getScene() != null) {
            getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                switch (event.getCode()) {
                    case ADD:
                        WorkingSurface.get().getSelectedTab().zoomIn();
                        break;
                    case SUBTRACT:
                        WorkingSurface.get().getSelectedTab().zoomOut();
                        break;
                }
            });

            getScene().setOnScroll(event -> {
                if(event.isControlDown()) {
                    if(event.getDeltaY() > 0) {
                        WorkingSurface.get().getSelectedTab().zoomIn();
                    } else {
                        WorkingSurface.get().getSelectedTab().zoomOut();
                    }
                    event.consume();
                }
            });
        } else {
            System.err.println("Cannot initialize ApplicationListeners because the Scene was not set!");
        }
    }
}
