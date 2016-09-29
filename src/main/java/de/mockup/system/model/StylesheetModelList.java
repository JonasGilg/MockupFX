package de.mockup.system.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;

/**
 * Holds a list of all ProjectStylesheets.
 * @author Janos Vollmer
 */
public class StylesheetModelList extends BaseModel {
	
	/**
	 * List of Stylesheets
	 */
	private List<StylesheetModel> stylesheets;
	private static final String KEY_ID = "stylesheet";
	private static final String KEY_PROPERTIES = "properties";

	public StylesheetModelList() {
		stylesheets = new ArrayList<StylesheetModel>();
	}
	
	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		JSONObject properties = data.getJSONObject(KEY_PROPERTIES);
		if(properties != null && properties.length() > 0) {
			Iterator<String> keys = properties.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				StylesheetModel m = new StylesheetModel();
				m.fromConfig(properties.getJSONObject(key));
				stylesheets.add(m);
			}
		}
	}
	
	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		JSONObject properties = new JSONObject();
		for(int i = 0; i < stylesheets.size(); i++) {
			properties.put(KEY_ID + stylesheets.get(i).getName(), stylesheets.get(i).toConfig());
		}
		config.put(KEY_PROPERTIES, properties);
		return config;
	}
	
	/**
	 * Returns a list of StylesheetModels
	 * @return List of StylesheetModels
	 */
	public List<StylesheetModel> getList() {
		return stylesheets;
	}
	/**
	 * Sets the StylesheetList
	 */
	public void setList(List<StylesheetModel> newList) {
		this.stylesheets = newList;
	}
	
	/**
	 * Adds a new Stylesheet to the list
	 * @param model StylesheetModel to add to the list
	 */
	public void add(StylesheetModel model) {
		stylesheets.add(model);
	}
	
	/**
	 * Removes a Stylesheet from the list
	 * @param model deleted Stylesheet
	 */
	public void remove(StylesheetModel model) {
		stylesheets.remove(model);
	}
	
	/**
	 * Removes a Stylesheet from the list
	 * @param index list index of deleted Stylesheet
	 */
	public void remove(int index) {
		stylesheets.remove(index);
	}
}