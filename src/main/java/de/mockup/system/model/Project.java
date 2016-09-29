package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.ui.gui.guiController.StylesheetController;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all project properties.
 */
public class Project extends BaseModel {

	private static final String KEY_NAME = "name";
	private static final String KEY_PATH = "storagePath";
	private static final String KEY_PROJECT_TYPE = "projectType";
	private static final String KEY_ID_SEQ = "idSeq";
	private static final String KEY_LOOK_AND_FEEL = "lookAFeel";
	private static final String KEY_LOOK_AND_FEEL_THEME = "lookAFeelTheme";
	private static final String KEY_ROOT_VIEW = "rootView";
    private static final String KEY_DESCRIPTION = "desc";
    private static final String KEY_STYLESHEET = "stylesheet";

	private String name;

	private String storagePath;

	private String lookAndFeel;

	private String lookAndFeelTheme;

	private String projectType;

	private int componentIdSeq = 0;

	private Map<Integer, ViewModel> viewStorage;

	private ViewModel rootView;

	private StylesheetModel stylesheet;
    private String descriptionText = "your description here";

    public Project() {
		this.viewStorage = new HashMap<>();
	}

    public Project(String path) {
        this.viewStorage = new HashMap<>();
        this.storagePath = path;
    }

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();
		data.put(KEY_NAME, name);
		data.put(KEY_PATH, storagePath);
		data.put(KEY_ID_SEQ, componentIdSeq);
		data.put(KEY_LOOK_AND_FEEL, lookAndFeel);
		data.put(KEY_LOOK_AND_FEEL_THEME, lookAndFeelTheme);
		data.put(KEY_PROJECT_TYPE, projectType);
        data.put(KEY_DESCRIPTION, descriptionText);
        //TODO fails tests data.put(KEY_STYLESHEET, stylesheet.getPssId());
		if (rootView != null) {
			data.put(KEY_ROOT_VIEW, rootView.getId());
		}
		return data;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		this.name = data.getString(KEY_NAME);
		this.storagePath = data.optString(KEY_PATH);
		this.lookAndFeel = data.optString(KEY_LOOK_AND_FEEL);
		this.lookAndFeelTheme = data.optString(KEY_LOOK_AND_FEEL_THEME);
		this.componentIdSeq = data.getInt(KEY_ID_SEQ);
		this.projectType = data.getString(KEY_PROJECT_TYPE);
        this.descriptionText = data.optString(KEY_DESCRIPTION, "");
		//TODO fails tests this.stylesheet = StylesheetController.getStylesheet(data.getString(KEY_STYLESHEET));
	}

	public void addView(ViewModel comp) {
		if (comp.getId() == null) {
			comp.setId(generateComponentId());
		}
		viewStorage.put(comp.getId(), comp);
	}

	public Map<Integer, ViewModel> getViewStorage() {
		return viewStorage;
	}


	public int generateComponentId() {
		componentIdSeq++;
		return componentIdSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getLookAndFeel() {
		return lookAndFeel;
	}

	public void setLookAndFeel(String lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public ViewModel getRootView() {
		return rootView;
	}

	public void setRootView(ViewModel rootView) {
		this.rootView = rootView;
	}

	public String getLookAndFeelTheme() {
		return lookAndFeelTheme;
	}

	public void setLookAndFeelTheme(String lookAndFeelTheme) {
		this.lookAndFeelTheme = lookAndFeelTheme;
	}

	public StylesheetModel getStylesheet() { return stylesheet; }

	public void setStylesheet(StylesheetModel stylesheet) { this.stylesheet = stylesheet; }

    public void setDescriptionText(String description) {
        this.descriptionText = description;
    }
    public String getDescriptionText(){
        return descriptionText;
    }
}
