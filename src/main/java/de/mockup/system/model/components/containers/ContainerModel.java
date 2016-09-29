package de.mockup.system.model.components.containers;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.ChildModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Baseclass for Containers, Panels...
 */

public class ContainerModel extends ChildModel {

	private static final String KEY_ITEMS = "items";

	private List<BaseComponentModel> items;

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_ITEMS, (this.items != null ? createJsonList(this.items) : null));
		return config;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		this.items = fromJsonList(data.optJSONArray(KEY_ITEMS));
	}

	public String getType() {
		return ModelTypes.Container.CONTAINER;
	}

	public void addItem(ChildModel child) {
		if (items == null) {
			items = new LinkedList<>();
		}
		synchronized (child) {
			items.add(child);
			child.setParent(this);
		}
	}

	public void removeItem(ChildModel child) {
		if (items != null) {
			items.remove(child);
		}
	}

	public List<BaseComponentModel> getItems() {
		return items;
	}
}
