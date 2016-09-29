package de.mockup.ui.gui.guiController;

import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.StylesheetModel;
import de.mockup.system.model.StylesheetModelList;
import org.apache.commons.io.FileUtils;

import java.util.List;
import java.io.File;
import java.io.IOException;

//TODO interacts directly with backend! Should be handled in lower layers (de.mockup.ui.controller.logic)

/**
 * Provides methods, to link Backend to Model, and Model to GUI.
 * @author Janos Vollmer
 */
public class StylesheetController {
	/**
	 * List of StylesheetModels
	 */
    private static StylesheetModelList stylesheets;

    /**
     * Savepath
     */
    private static String path;

    /**
     * Return the list of Stylesheets
     * @return List of StylesheetModels
     */
    public static List<StylesheetModel> getStylesheets() {
		initList();
    	return stylesheets.getList();
    }

    /**
     * Find a Stylesheet by index
     * @return Stylesheet of given index
     */
	public static StylesheetModel getStylesheet(int index) {
		List<StylesheetModel> list = getStylesheets();
		if(list.size() <= index || index < 0) {
			return null;
		}
		return list.get(index);
	}

	/**
	 * Find Stylesheet by ID
	 * @param id Pssid
	 * @return Returns StylesheetModel of given ID
	 */
	public static StylesheetModel getStylesheet(String id) {
		initList();
		for(StylesheetModel m : stylesheets.getList()) {
			if(m.getPssId().equals(id)) return m;
		}
		return null;
	}

	/**
	 * Find index of given Stylesheet
	 * @param sm Stylesheet
	 * @return Index of Stylesheet
	 */
	public static int find(StylesheetModel sm) {
		if(sm == null) {
			return -2;
		}
		List<StylesheetModel> list = getStylesheets();
		for(int i = 0; i < list.size(); i++) {
			if(sm == list.get(i)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Add a new Stylesheet
	 * @param model New Model
	 */
	public static void addStylesheet(StylesheetModel model) {
		initList();
		stylesheets.add(model);
    }

	/**
	 * Remove Stylesheet by index
	 */
	public static void removeStylesheet(int index) {
		initList();
		stylesheets.remove(index);
	}

	/**
	 * Initiates the list of Stylesheets if not yet done and loads data from backend to fill the list
	 */
	public static void initList() {
		if(stylesheets == null){
			stylesheets = new StylesheetModelList();
			loadStylesheets();
		}
    }

	/**
	 * Persisting the current list of Stylesheets
	 */
    public static void saveStylesheets() {
    	if(path == null) path = getPath();
    	File file = new File(path);
    	try {
    		if(!file.exists()) {
    			if(!file.createNewFile()) {
    				throw new SystemException(SystemErrorCodes.ENTITY_WRITE_ERROR);
    			}
    		}
    		FileUtils.writeStringToFile(file, stylesheets.toConfig().toString());
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Loading a list of previously persisted Stylesheets
     */
    public static void loadStylesheets() {
    	if(path == null) path = getPath();
    	File file = new File(path);
    	if(file != null && file.exists()) {
    		try {
    			JSONObject config = new JSONObject(FileUtils.readFileToString(file));
    			stylesheets.fromConfig(config);
    		} catch(IOException e) {
    			e.printStackTrace();
    		} catch(SystemException e) {
    			e.printStackTrace();
    		}
    	} else {
			createStartList();
		}
    }

    /**
     * Renaming a Stylesheet
     */
    public static void renameStylesheet(String oldName, String newName) {
    	for(StylesheetModel s : stylesheets.getList()) {
    		if(s.getName().equals(oldName)) {
    			s.setName(newName);
    			break;
    		}
    	}
    }

    /**
     * If no Stylesheets are persisted and the loading method is called, this Method creates a new list with one standard Stylesheet
     */
    private static void createStartList() {
    	StylesheetModel m = new StylesheetModel();
    	m.setName("Beispiel Stylesheet");
    	stylesheets.add(m);
    }

    /**
     * Local storage path, where Stylesheets are persisted. Currently it's in the user-directory/.mockup/stylesheets.json
     * @return String of the savepath
     */
    private static String getPath() {
    	return System.getProperty("user.home") + System.getProperty("file.separator") + ".mockup" +
    			System.getProperty("file.separator") + "stylesheets.json";
    }
}
