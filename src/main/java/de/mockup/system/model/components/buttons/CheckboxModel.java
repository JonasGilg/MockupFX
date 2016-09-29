package de.mockup.system.model.components.buttons;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

/**
 * Simple CheckBox
 */

public class CheckboxModel extends ChildModel {

	private static final String KEY_LABEL = "label";
	private static final String KEY_ICON = "icon";
	private static final String KEY_BUTTON_SELECTED = "selected";
	private static final String KEY_BORDER = "border";

	private String label;
	private String icon;
	private boolean selected;
	private boolean border;


	@Override
	public String getType() {
		return ModelTypes.Fields.CHECKBOX;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		label = data.optString(KEY_LABEL);
		icon = data.optString(KEY_ICON);
		selected = data.optBoolean(KEY_BUTTON_SELECTED);
		border = data.optBoolean(KEY_BORDER);
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();
		data.put(KEY_ICON, icon);
		data.put(KEY_LABEL, label);
		data.put(KEY_BUTTON_SELECTED, selected);
		data.put(KEY_BORDER, border);
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}
}
