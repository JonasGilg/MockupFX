 
package de.mockup.system.model.components.buttons;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

/**
 * Simple Button
 */

public class ButtonModel extends ChildModel {

	private static final String KEY_LABEL = "label";
	private static final String KEY_ICON = "icon";
	private static final String KEY_DEFAULTCAPABLE = "defaultCapable";
	private static final String KEY_DEFAULTBUTTON = "defaultButton";
	
	private String label;
	private String icon;
	private boolean defaultCapable;
	private boolean defaultButton;
		
	@Override
	public String getType() {
		return ModelTypes.Button.BUTTON;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		/**label = data.getString(KEY_LABEL);
		icon = data.optString(KEY_ICON);
		defaultCapable = data.optBoolean(KEY_DEFAULTCAPABLE);
		defaultButton = data.optBoolean(KEY_DEFAULTBUTTON);**/
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();/**
		data.put(KEY_ICON, icon);
		data.put(KEY_LABEL, label);
		data.put(KEY_DEFAULTCAPABLE, defaultCapable);
		data.put(KEY_DEFAULTBUTTON, defaultButton);**/
		return data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isDefaultCapable() {
		return defaultCapable;
	}

	public void setDefaultCapable(boolean defaultCapable) {
		this.defaultCapable = defaultCapable;
	}

	public boolean isDefaultButton() {
		return defaultButton;
	}

	public void setDefaultButton(boolean defaultButton) {
		this.defaultButton = defaultButton;
	}
	

}
