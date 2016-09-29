package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;

/**
 * Contains SystemConfigurations
 */
public class AppConfig extends BaseModel {

	private static final String KEY_APPLICATION_FOLDER = "appFolder";

	private String appFolder;

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		this.appFolder = data.optString(KEY_APPLICATION_FOLDER);
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_APPLICATION_FOLDER, this.appFolder);
		return config;
	}

	public String getAppFolder() {
		return System.getProperty("java.io.tmpdir");
	}

	public void setAppFolder(String appFolder) {
		this.appFolder = appFolder;
	}
}
