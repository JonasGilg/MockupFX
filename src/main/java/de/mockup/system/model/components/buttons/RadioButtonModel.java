package de.mockup.system.model.components.buttons;


/**
 * RadioButton
 */

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

public class RadioButtonModel extends ChildModel {

	/*private static final String KEY_LABEL = "label";
	private static final String KEY_ICON = "icon";
	private static final String KEY_TEXT = "text";
	private static final String KEY_SELECTED = "selected";

	private String label;
	private String icon;
	private String text;
	private boolean selected;*/

	@Override
	public String getType() {
		return ModelTypes.Fields.RADIO;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		/* label = data.getString(KEY_LABEL);
		icon = data.optString(KEY_ICON);
		text = data.optString(KEY_TEXT);
		selected = data.optBoolean(KEY_SELECTED);*/
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();
		/*data.put(KEY_ICON, icon);
		data.put(KEY_LABEL, label);
		data.put(KEY_TEXT, text);
		data.put(KEY_SELECTED, selected);*/
		return data;
	}
/*
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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}*/
}
