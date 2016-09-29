package de.mockup.ui.gui.guiController;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.Project;
import de.mockup.system.model.StylesheetModel;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.Application;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentType;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.windows.HierarchyTree;
import de.mockup.ui.gui.windows.MainFrame;
import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.dialogs.NewProjectDialog;

import java.util.Date;

/**
 * Funktion:	Gibt Aktionen der GUI an das Project() weiter
 * Verbindung: GUI und Project()
 */
public class ProjectController {

	private static Project currentProject;

	/**
	 * default project name
	 */
	public static final String PROJECT_NAME = "NewProject";

	/**
	 * default project type
	 */
	public static final ContentType PROJECT_TYPE = ContentType.SWING;

	/**
	 * default save path
	 */
	//TODO SAVEPATH isnt working on Mac
	//public static String SAVEPATH = new java.io.File("Saves").getAbsolutePath();
	public static final String SAVEPATH = new java.io.File(System.getProperty("user.home")+System.getProperty("file.separator")).getAbsolutePath();

	/**
	 * default look and feel (Just at project creation, can be changed immediately after)
	 */
	public static String lookAndFeel = "Nimbus";


	public static boolean snapEnable = true;

	/**
	 * creates a new project with following parameters
	 *
	 * @param projectName
	 * @param type
	 * @param savePath
	 * @param lookAndFeel
	 */
	public static void createNewProject(String projectName, ContentType type, String savePath, String lookAndFeel) {
		currentProject = GuiToControllerFacade.newProject(projectName, String.valueOf(type), savePath, lookAndFeel);
		System.out.println("Project created with Savepath: " + currentProject.getStoragePath());
		GuiToContentFacade.initContent();
	}

	/**
	 * starts the currently loaded project.
	 * it clears the surface from old views and opens all views of the current project
	 */
	public static void startTheProject() {

		MainFrame.get().setLaFButtonState(true);
		MainFrame.get().setProjectOverviewButtonState(true);
		MainFrame.get().setTemplateButtonState(true);
		MainFrame.get().setStringEditorButtonState(true);
		MainFrame.get().setIconLibraryButtonState(true);
		MainFrame.get().setProjectStyleSheetButtonState(true);
		MainFrame.get().setStoryBookEditorButtonState(true);
		MainFrame.get().setSaveButtonState(true);
		MainFrame.get().setExportButtonState(true);
		MainFrame.get().setControlModeButtonState(true);
		HierarchyTree.get().initTree(currentProject.getName());
		callWorkSurfaceToOpenViews();
	}

	/**
	 * adds a new View to the project and shows it on the working surface
	 */
	public static void addNewView() {
		if (!(currentProject == null)) {
			ViewModel viewModel = GuiToControllerFacade.createView();
			addViewToUI(viewModel);
		} else {
			new NewProjectDialog(Application.getPrimaryStage());
		}
	}

	private static void addViewToUI(ViewModel viewModel) {
		ContentView contentView = null;
		try {
			contentView = ModelManager.get().convert(viewModel);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		WorkingSurface.get().addView(contentView);
	}

	/**
	 * returns the stylesheet from the current project
	 */
	public static StylesheetModel getStylesheet() {
		if (!hasProject())
			return null;
		return currentProject.getStylesheet();
	}

	/**
	 * sets the stylesheet to the current project
	 */
	public static void setStylesheet(StylesheetModel sm) {
		if (!hasProject())
			return;
		currentProject.setStylesheet(sm);
	}

	/**
	 * loads the given Project and shows it on the workingsurface
	 *
	 * @param p Project
	 */
	public static void openProject(Project p) {
		currentProject = p;

		GuiToContentFacade.initContent();
		if (currentProject.getLookAndFeel() != null) {
			GuiToContentFacade.LaF.setCurrentLaF(currentProject.getLookAndFeel(), currentProject.getLookAndFeelTheme());
		}
		startTheProject();
	}



    public static void deleteView(ContentView view) {
        WorkingSurface.get().deleteView(view);
        GuiToControllerFacade.deleteView((ViewModel)view.getComponentModel());
    }

	public static boolean hasProject() {
		return !(currentProject == null);
	}

	public static void setProjectName(String name) {
		HierarchyTree.get().renameRoot(name);
        currentProject.setName(name);
	}

	public static void saveProjectAs(String name, String path) {
		setProjectName(name);
		try {
			GuiToControllerFacade.saveAsProject(path);
		} catch (SystemException ignored) {
		}

	}

	public static void saveProject() {
		try {

            GuiToControllerFacade.saveProject();
            //set ViewModels clean
            for (ContentView cView: WorkingSurface.get().getViews()) {
                ((ViewModel) cView.getComponentModel()).setDirty(false);
            }
		} catch (SystemException ex) {
			ex.printStackTrace();
		}

	}
    public static boolean isProjectDirty(){
        for (ViewModel vm: currentProject.getViewStorage().values()){
            if (vm.isDirty()) return true;
        }
        return false;
    }

	public static String getProjectName() {
		return currentProject.getName();
	}

	public static String getSavepath() {
		return currentProject.getStoragePath();
	}

	public static ContentType getType() {
		return PROJECT_TYPE;
	}

	public static Date getDate() {
		//TODO return currentProject.getCreatedOn();
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Removes all tabs on the working surface. is called before loading/starting another project to the WorkingSurface.
	 */
	private static void clearWorkingSurface() {
        WorkingSurface.get().deleteViews();
	}

	/**
	 * sets the currentProject = null
	 */
	public static void setProjectNull() {
		currentProject = null;
		clearWorkingSurface();
	}

	private static void callWorkSurfaceToOpenViews() {
		clearWorkingSurface();
        WorkingSurface.get().toggleOverview(true);

		for (BaseComponentModel model : currentProject.getViewStorage().values()) {
			try {
				ContentView view = ModelManager.get().convert(model);
				WorkingSurface.get().addView(view);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		WorkingSurface.get().enableOverview(true);

	}

	public static void undoFunction() {
		GuiToControllerFacade.undo();
		MainFrame.get().setUndoRedoButtonState();
		GuiToContentFacade.updateViewGraphics(WorkingSurface.get().getSelectedViewNode());
		PropertyWindow.setComponentOfInterest(PropertyWindow.getComponentofInterest());
	}

	public static void redoFunction() {
		GuiToControllerFacade.redo();
		MainFrame.get().setUndoRedoButtonState();
		GuiToContentFacade.updateViewGraphics(WorkingSurface.get().getSelectedViewNode());
		PropertyWindow.setComponentOfInterest(PropertyWindow.getComponentofInterest());
	}

	public static void deleteComponent(ContentComponent component) {
		SelectionManager.deselect();
		component.getParent().removeChild(component);
		HierarchyTree.get().remove(component);
		component.notifyObservers();
	}

    public static void setProjectDescription(String description) {
         currentProject.setDescriptionText(description);
    }

    public static String getProjectDescriptionText(){
        return currentProject.getDescriptionText();
    }
}
