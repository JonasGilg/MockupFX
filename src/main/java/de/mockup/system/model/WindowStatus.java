package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;

/**
 * Defines the status of a window
 */
public class WindowStatus extends BaseModel {

	private static final String KEY_CLAZZ = "clazz";
	private static final String KEY_WIDTH = "width";
	private static final String KEY_HEIGHT = "height";

	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";

	private static final String KEY_HIDDEN = "hidden";

	private String clazz;
	private double height;
	private double width;
	private double x;
	private double y;
	private boolean hidden;

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_CLAZZ, clazz);
		config.put(KEY_WIDTH, width);
		config.put(KEY_HEIGHT, height);
		config.put(KEY_X, x);
		config.put(KEY_Y, y);
		config.put(KEY_HIDDEN, hidden);
		return config;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		this.clazz = data.getString(KEY_CLAZZ);
		this.width = data.getDouble(KEY_WIDTH);
		this.height = data.getDouble(KEY_HEIGHT);
		this.x = data.getDouble(KEY_X);
		this.y = data.getDouble(KEY_Y);
		this.hidden = data.getBoolean(KEY_HIDDEN);
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
