package de.mockup.system.model.components.containers;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;

/**
 * Model for Views
 */
public class ViewModel extends ContainerModel {

	public static final String KEY_NAME = "title";
	public static final String KEY_STORAGE = "storageName";

	private String title;

	/**
	 * Default the dirty flag is true because the model is not stored.
	 */
	private boolean dirty = true;

	@Override
	public void setParent(ContainerModel parent) {
		throw new IllegalArgumentException("Views dont have parents!");
	}

	@Override
	public ContainerModel getParent() {
		throw new IllegalArgumentException("Views dont have parents!");
	}

	@Override
	public String getType() {
		return ModelTypes.Container.VIEW;
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_NAME, title);
		return config;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		title = data.optString(KEY_NAME);
	}

	public String getStorageName() {
		return getType() + "_" + this.getId();
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public String getTitle() {
		return title;
	}

}
