package de.mockup.ui.gui.windows.dialogs.stringeditor;

import java.io.File;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.gui.windows.WorkingSurface;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 * specific controller for the StringEditor, which uses javaFX
 */
public class StringEditorController {
	
	/**
	 * the ResourceBundle for StringEditor
	 */
	public static final ResourceBundle resBundle = ResourceBundle.getBundle("properties.StringEditorBundle");

	/**
	 * the view representing 
	 */
	private StringEditorView view;
	private StringEditorService service;
	private static StringEditorController instance = null;
	
	
	private StringEditorController(){
		service = new StringEditorService(WorkingSurface.get().getViews());
	}
	
	/**
	 * Get the StringEditorController
	 * @return
	 */
	public static synchronized StringEditorController getInstance() {
		if (instance == null) {
			instance = new StringEditorController();
		}
		return instance;
	}
	
	/**
	 * Shows the View of the StringEditor.
	 */
	public void showView(){
		view = new StringEditorView(this);
		view.show();
	}
	
	
	/**
	 * method which will be executed when the OK or Apply button is pressed.
	 * Data in the {@link TableView} will be stored into .properties
	 */
	public void applyAction(){
		service.dataToProperties();
		
	}

	/**
	 * method which will be executed when the Cancel-Button is pressed
	 * A Dialog will ask the user whether he really wants to exit the StringEditor without saving progress
	 */
	public void cancelAction(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(resBundle.getString("warning"));
		Optional<ButtonType> result = alert.showAndWait();
		result.ifPresent( type ->{
			if(type == ButtonType.OK) view.close();
			//else nothing
		});

	}
	
	/**
	 * Saves all progress in the StringEditor
	 * @return success of the operation
	 */
	public boolean save(File projectDir){
		System.out.println("Strings gespeichert in : " + projectDir.getPath());
		return service.savePropertiesToFiles(projectDir.getPath());
	}
	
	/**
	 * Loads data from the project folder to initiate the StringEditorServie's data
	 * @param projectDir
	 * @return success of the operation
	 */
	public boolean load(File projectDir){
		System.out.println("Strings laden von : " + projectDir.getPath());
		boolean retVal = service.loadPropertiesFromFiles(projectDir.getPath());
		service.initiateData(WorkingSurface.get().getViews());
		return retVal;
	}
	
	/**
	 * Updates the name of a component in the internal data structure
	 * @param comp
	 * @param oldname
	 * @param newname
	 */
	public void changeComponentName(ContentComponent<?> comp,String oldname, String newname){
		for(String s: comp.getStringKeys()){
			if(s.startsWith(newname)){
				service.replaceStringKey(s, s.replace(oldname, newname));
			}
		}
	}
	
	/**
	 * Get languages to use 
	 * @return languages in use by the StringEditorService
	 */
	public Collection<Locale> getLanguages(){
		return service.getLanguages();
	}
	
	public ObservableList<TableDataModel> getModelData(){
		return service.getModelData();
	}
	
}
