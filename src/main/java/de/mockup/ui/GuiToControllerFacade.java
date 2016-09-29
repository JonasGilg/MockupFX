package de.mockup.ui;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.Project;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.ChildModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.ui.controller.logic.IconlibSettings;
import de.mockup.ui.controller.logic.Management;
import de.mockup.ui.controller.logic.Utility;
import javafx.stage.FileChooser;

import javax.swing.text.ComponentView;
import javax.swing.undo.AbstractUndoableEdit;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * GuiToControllerFacade for the GUI to trigger Business Logic
 */
public class GuiToControllerFacade {

    /**
     * Creates a new Project and returns it to the UI.
     *
     * @param name        of the Project
     * @param type        of the Project for Example: Java Swing or Website
     * @param savePath    where the Project is being saved
     * @param lookAndFeel the style of the UI
     * @return an empty Project with the given preferences
     */
    public static synchronized Project newProject(String name, String type, String savePath, String lookAndFeel) {
        return Management.newProject(name, type, savePath, lookAndFeel);
    }

    /**
     * Loads an already exising Project and returns it to the UI.
     *
     * @param path Path to the Project.
     * @return an already existing Project
     * @throws SystemException
     */
    public static Project loadProject(String path) throws SystemException {

        return Management.loadProject(path);
    }

    /**
     * Saves the Project at the default location which is saved in the Project.
     *
     * @return if the Project was saved successfully
     * @throws SystemException
     */
    public static boolean saveProject() throws SystemException {
        return Management.saveProject();
    }

    /**
     * Saves the Project at a specified Path.
     *
     * @param path Path to save the project.
     * @return if the Project was saved successfully
     * @throws SystemException
     */
    public static boolean saveAsProject(String path) throws SystemException {
        return Management.saveAsProject(path);
    }

    public static void copy(ArrayList<ChildModel> list) {
        Management.copyComponents(list);
    }

    public static boolean cut(ArrayList<ChildModel> list) {
        return Management.cutElements(list);
    }

    public static ArrayList<ChildModel> paste(ContainerModel c) {
        return Management.pasteComponents(c);
    }

    /**
     * Has to be invoked before edits are being done.
     *
     * @param undoRedo implements the actions to be taken on undo and redo
     */
    public static synchronized void changeAction(AbstractUndoableEdit undoRedo) {
        Management.changeAction(undoRedo);
    }

    public static synchronized void undo() {
        Management.undo();
    }

    public static synchronized void redo() {
        Management.redo();
    }

    /**
     * @return is there something to be undone?
     */
    public static synchronized boolean canUndo() {
        return Management.canUndo();
    }

    /**
     * @return is there something to be redone?
     */
    public static synchronized boolean canRedo() {
        return Management.canRedo();
    }

    /**
     * Creates a ViewModel
     *
     * @return new Viewmodel
     */
    public static synchronized ViewModel createView() {
        return Management.createView();
    }

    /**
     * Deletes a ViewModel from Project
     *
     * @param index unique number for a ViewModel
     */
    public static synchronized boolean deleteView(int index) {
        return Management.deleteView(index);
    }

    public static synchronized boolean deleteView(ViewModel model) {
        return Management.deleteView(model);
    }

    public static synchronized ViewModel clearViews() {
        return Management.clearViews();
    }

    //TODO: Connect a BaseComponentModel to the View it should lead to
    public static synchronized void connectToView(BaseComponentModel b, ViewModel m) {

    }

    /**
     * changes a parent (e.g. drag button from panel1 and drop to panel 2 outside of panel1)
     *
     * @param newparent the new parent for child
     * @param child
     * @return true, if change has worked
     */
    public static synchronized boolean changeParent(ContainerModel newparent, ChildModel child) {
        return Management.changeParent(newparent, child);
    }
    /**
     * @param parent    in which the Component got dropped
     * @param modelType of the Component e.g. Button, Label
     * @return The Model that needs to be converted to a JComponent
     */

    public static synchronized BaseComponentModel addComponent(ContainerModel parent, String modelType) {
        return Management.createComponent(parent, modelType);
    }

    public static synchronized boolean deleteComponent(ChildModel c) {
        return Management.deleteComp(c);
    }

    public static synchronized void toPDF(ComponentView view, String filename) {
        //TODO
    }

    public static synchronized void toJPG(ComponentView view, String filename) {
        //TODO
    }

    public static synchronized void configIconFilechooser(FileChooser fc) {
        IconlibSettings.configureIconFilechooser(fc);
    }

    public static synchronized boolean createIconAndSavePermanently(File file) {
        return (IconlibSettings.createAndSavePersistent(file));
    }

}
