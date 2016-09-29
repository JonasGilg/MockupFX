package de.mockup.system.util;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONArray;
import de.mockup.system.json.JSONException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.containers.ViewModel;

import java.util.Map;
import java.util.Set;

/**
 * Utility class for Models
 */
public class ModelUtil {

	public JSONObject createViewStorageObj(ViewModel model) {
		JSONObject o = new JSONObject();
		o.put(ViewModel.KEY_NAME, model.getTitle());
		o.put(ViewModel.KEY_STORAGE, model.getStorageName());
		return o;
	}


	public JSONArray createViewStorageJson(Map<Integer, ViewModel> viewStorage) throws JSONException {
		JSONArray views = new JSONArray();
		Set<Integer> it = viewStorage.keySet();
		for (Integer key : it) {
			ViewModel view = viewStorage.get(key);
			views.put(createViewStorageObj(view));
		}
		return views;
	}

	public ViewModel createViewFromJson(JSONObject data) throws SystemException {
		ViewModel comp = ModelManager.get().createModel(ModelTypes.Container.VIEW);
		comp.fromConfig(data);
		return comp;
	}

}
