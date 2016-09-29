package de.mockup.system.model.components;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.BaseModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Basis component clazz
 */
public abstract class BaseComponentModel extends BaseModel {

	private static final String KEY_ID = "id";
	protected static final String KEY_TYPE = "type";
	private static final String KEY_WIDTH = "width";
	private static final String KEY_HEIGHT = "height";
	private static final String KEY_PROPERTIES = "properties";

	private Integer id;
	// TODO Width and height stored in properties?
	private Integer width;
	private Integer height;
	private final Map<String, Object> propertyMap = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public void fromConfig(JSONObject data) throws SystemException {
		this.id = data.optInteger(KEY_ID);
		this.width = data.optInteger(KEY_WIDTH);
		this.height = data.optInteger(KEY_HEIGHT);

		JSONObject properties = data.getJSONObject(KEY_PROPERTIES);
		if (properties != null && properties.length() > 0) {
			Iterator<String> keys = properties.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				this.propertyMap.put(key, properties.get(key));
			}
		}
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_ID, id);
		try {
			config.put(KEY_TYPE, getType());
		} catch (Exception e) {
			//TODO Invalid Model!
			e.printStackTrace();
		}
		config.put(KEY_WIDTH, width);
		config.put(KEY_HEIGHT, height);

		JSONObject jsonProperties = new JSONObject();
		for (Map.Entry<String, Object> p : propertyMap.entrySet()) {
			jsonProperties.put(p.getKey(), p.getValue());
		}
		config.put(KEY_PROPERTIES, jsonProperties);

		return config;
	}

	/**
	 * <p>Abstruct getType. Defines the component type.</p>
	 * <p>Examples: "container", "button"...</p>
	 *
	 * @return String that tells the type.
	 */
	public abstract String getType();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setProperty(String key, Object value) {
		this.propertyMap.put(key, value);
	}

	public void removeProperty(String key) {
		this.propertyMap.remove(key);
	}

	public Map<String, Object> getPropertyMap() {
		return this.propertyMap;
	}

	public Object getProperty(String key) {
		return this.propertyMap.get(key);
	}

}