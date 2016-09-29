package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONArray;
import de.mockup.system.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Model to store the status of the application
 */
public class AppStatus extends BaseModel {

	private static final String KEY_WINDOWS = "windows";
	private static final String KEY_PROEJCTS = "projects";

	private List<WindowStatus> windows;
	private Stack<String> latestProjects;

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		JSONArray windows = data.optJSONArray(KEY_WINDOWS);
		List<WindowStatus> windowStati = new ArrayList<>(windows.length());
		for (int i = 0; i < windows.length(); i++) {
			JSONObject status = windows.getJSONObject(i);
			WindowStatus statusModel = new WindowStatus();
			statusModel.fromConfig(status);
			windowStati.add(statusModel);
		}
		this.windows = windowStati;
		Stack<String> latestProjects = new Stack<>();
		JSONArray projects = data.optJSONArray(KEY_PROEJCTS);
		for (int i = 0; i < projects.length(); i++) {
			latestProjects.push(projects.getString(i));
		}
		this.latestProjects = latestProjects;
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_PROEJCTS, new JSONArray(latestProjects));
		JSONArray data = new JSONArray(windows.size());
		for (WindowStatus status : windows) {
			data.put(status.toConfig());
		}
		config.put(KEY_WINDOWS, data);
		return config;
	}

	public List<WindowStatus> getWindows() {
		return windows;
	}

	public void setWindows(List<WindowStatus> windows) {
		this.windows = windows;
	}

	public Stack<String> getLatestProjects() {
		return latestProjects;
	}

	public void setLatestProjects(Stack<String> latestProjects) {
		this.latestProjects = latestProjects;
	}
}
