package de.mockup.ui.controller.logic;

import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.Project;
import de.mockup.system.model.TemplateModel;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.ChildModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.system.service.AppService;
import de.mockup.system.service.ProjectService;
import de.mockup.system.service.TemplateService;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.content.ContentComponent;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class contains most of the Business Logic in the App concerning Project- Template- component- and Viewhandling.
 */
public class Management {

    private static Project curProject = null;
    private static UndoManager um = null;
    private static ArrayList<ChildModel> copyList = new ArrayList<>();

    /**
     * Creates a new Project
     *
     * @param name        the name for the project
     * @param type        the type for the project
     * @param savePath    is the path in the filesystem where the project will be saved
     * @param lookAndFeel defines the lookAndFeel of the project
     * @return a new Project
     */
    public static synchronized Project newProject(String name, String type, String savePath, String lookAndFeel) {
        curProject = new Project();
        curProject.setName(name);
        curProject.setStoragePath(savePath);
        curProject.setProjectType(type);
        curProject.setLookAndFeel(lookAndFeel);

        ViewModel rootView = createView();
        curProject.setRootView(rootView);

        um = new UndoManager();

        return curProject;
    }

    //TODO: javadoc
    private static AppConfig getAppConfig() throws SystemException {
        AppService appservice = Bundle.getService(AppService.class);
        return appservice.getAppConfig();
    }

    /**
     * loads a project from the filesystem
     * @param path the path where the project is located in the filesystem
     * @return an existing project into the working surface
     * @throws SystemException
     */
    public static synchronized Project loadProject(String path) throws SystemException {
        ProjectService projectService = Bundle.getService(ProjectService.class);
        curProject = projectService.getProject(getAppConfig(), new Project(path));
        um = new UndoManager();
        return curProject;
    }

    /**
     * Saves a project to a filesystem
     * @return if saving the project has been determined without errors
     * @throws SystemException
     */
    public static synchronized boolean saveProject() throws SystemException {

        curProject.setLookAndFeel(GuiToContentFacade.LaF.getLaF());
        curProject.setLookAndFeelTheme(GuiToContentFacade.LaF.getTheme());

        if (!SaveisValid()) {
            return false;
        }
        ProjectService projectService = Bundle.getService(ProjectService.class);
        projectService.saveProject(getAppConfig(), curProject);
        return true;
    }

    /**
     * Saves the project in the given path
     * @param path the new save-path for the project
     * @return if saving the project has been determined without errors.
     * @throws SystemException
     */
    public static synchronized boolean saveAsProject(String path) throws SystemException {
        curProject.setStoragePath(path);
        return saveProject();
    }

    //TODO: write javadoc
    public static synchronized void saveTemplate(String name, ContentComponent component) throws SystemException {
        ModelManager modelManager = Bundle.getService(ModelManager.class);
        TemplateService templateService = Bundle.getService(TemplateService.class);

        BaseComponentModel model = modelManager.convert(component);
        TemplateModel templateModel = modelManager.createTemplate(model.getType() + model.getId(), model);
        if (!name.isEmpty()) {
            templateModel.setName(name);
        }
        templateService.saveTemplate(templateModel);
    }

    //TODO: write javadoc
    public static synchronized BaseComponentModel createComponentFromTemplate(ContainerModel parent, String typeParam) {
        ChildModel childModel = null;
        try {
            ModelManager modelManager = Bundle.getService(ModelManager.class);
            TemplateService templateService = Bundle.getService(TemplateService.class);

            int id = Integer.parseInt(typeParam.replace(ModelTypes.TEMPLATE_PREFIX, ""));
            TemplateModel templateModel = templateService.findById(id);
            childModel = modelManager.createModel(templateModel);

            /*TODO Remove debug code
            * START DEBUG CODE*/
            System.out.println(childModel.getClass().getName());
            if (childModel instanceof ContainerModel)
                System.out.println(((ContainerModel) childModel).getItems().size());
            /*END DEBUG CODE*/

            childModel.setId(curProject.generateComponentId());
            childModel.setCreatedOn(new Date());
            childModel.setModifiedOn(new Date());
            parent.addItem(childModel);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return childModel;
    }

    /**
     * Creates a BaseComponent when a Component is created to be shown in the working surface
     * @param parent the parent container for the BaseComponentModel
     * @param modelType the type of model which should be created
     * @return a new Component
     */
    public static synchronized BaseComponentModel createComponent(ContainerModel parent, String modelType) {
        ChildModel childModel = null;
        try {
            childModel = ModelManager.get().createModel(modelType);
            childModel.setId(curProject.generateComponentId());
            parent.addItem(childModel);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return childModel;
    }

    /**
     * Deletes a component
     * @param g the component which should be deleted
     * @return whether delete has worked or not
     */
    public static synchronized boolean deleteComp(ChildModel g) {
        ContainerModel parent = g.getParent();
        parent.removeItem(g);
        return true;
    }

    /**
     * Changes the parent of a component, if it has been moved to another parent
     * @param newparent the new parent, destination of drop
     * @param child the child component which gets a new parent
     * @return whether the change has worked or not
     */
    public static synchronized boolean changeParent(ContainerModel newparent, ChildModel child) {
        if (newparent != null && child != null) {
            if (child.getParent() != null) {
                child.getParent().removeItem(child);
            }
            newparent.addItem(child);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a View when a new View is created in the working surface
     * @return a new View
     */
    public static synchronized ViewModel createView() {
        try {
            ViewModel newView = ModelManager.get().createModel(ModelTypes.Container.VIEW);
            newView.setId(curProject.generateComponentId());

            newView.setTitle("view " + (curProject.getViewStorage().size() + 1));
            curProject.addView(newView);

            return newView;
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete a View from the project
     * @param index the index of the consecutive numbering of Views
     * @return whether delete has worked or not
     */
    public static synchronized boolean deleteView(int index) {
        if (viewCanBeDeleted(index)) {
            curProject.getViewStorage().remove(index);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a View from the project
     * @param model the View which should be deleted
     * @return whether delete has worked or not
     */
    public static synchronized boolean deleteView(ViewModel model) {
        if (viewCanBeDeleted(model.getId())) {
            curProject.getViewStorage().remove(model.getId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes all Views from the project and creates a fresh first one for a new project
     * @return the fresh first View
     */
    public static synchronized ViewModel clearViews() {
        for (int i = 0; i < curProject.getViewStorage().size(); i++) {
            curProject.getViewStorage().remove(i);
        }
        ViewModel firstDefault = new ViewModel();
        firstDefault.setTitle("view 1");
        curProject.addView(firstDefault);

        return firstDefault;
    }

    /**
     * Delegates a checking method/validation to the ControllSettings
     * @return whether saving is valid or not
     */
    private static synchronized boolean SaveisValid() {
        return (ControlSettings.projectIsValid(curProject));
    }

    /**
     * Checks if View is already in Project.
     *
     * @param index of View that should be deleted
     * @return true, if View is in storage. Otherwise false
     */
    private static boolean viewCanBeDeleted(int index) {
        return (ControlSettings.viewCanBeDeleted(curProject, index));
    }


    /**
     * For every change made, this feature adds this change to vector.
     *
     * @param undoRedo is the changed Component
     */
    public static synchronized void changeAction(AbstractUndoableEdit undoRedo) {
        um.addEdit(undoRedo);
    }

    /**
     * Undo the last action on the undo-stack
     */
    public static synchronized void undo() {
        if (canUndo() && getLimit() != 0) {
            um.undo();
        }
    }

    /**
     * Redo the last action which has been undo-ed on the undo-stack
     */
    public static synchronized void redo() {
        if (canRedo() && getLimit() != 0) {
            um.redo();
        }
    }

    /**
     * Checks if Undo can be executed
     * @return whether it can or can't be undo-ed
     */
    public static synchronized boolean canUndo() {
        return um.canUndo();
    }

    /**
     * Checks if Redo can be executed
     * @return whether it can or can't be redo-ed
     */
    public static synchronized boolean canRedo() {
        return um.canRedo();
    }

    /**
     * The limit of elements to be undo/redo-ed
     * @return the number of the limit
     */
    private static synchronized int getLimit() {
        return um.getLimit();
    }

    /**
     * creates new childmodels out of given Arraylist and adds to temporary copyList.
     *
     * @param list List of ChildModels to be copied
     */
    public static synchronized void copyComponents(ArrayList<ChildModel> list) {
        if (copyList == null) {
            copyList = new ArrayList<>();

        } else {
            copyList.clear();
        }

        for (ChildModel childModel : list) {

            try {
                ChildModel cmCopy = ModelManager.get().copy(childModel);
                copyList.add(cmCopy);
                cmCopy.setId(curProject.generateComponentId());

            } catch (SystemException e) {
                e.getMessage();
            }

        }
    }

    /**
     * Paste the components which have been copied or cut into the working surface
     * @param parent parent of the childs, so all childs are temporarily saved in a copyList
     * @return the list of Components
     */
    public static synchronized ArrayList<ChildModel> pasteComponents(ContainerModel parent) {
        copyList.forEach(parent::addItem);
        return copyList;
    }

    /**
     * Is like copying the components to the list and removing them from the View
     *
     * @param list list of components which should be cut from the View
     * @return wether the method has worked or not
     */
    public static synchronized boolean cutElements(ArrayList<ChildModel> list) {
        if (list != null) {
            copyComponents(list);

            for (ChildModel cm : list) {
                cm.getParent().removeItem(cm);
            }
            return true;
        }
        return false;
    }
}
