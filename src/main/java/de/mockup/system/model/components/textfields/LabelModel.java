package de.mockup.system.model.components.textfields;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

/**
 * Basic LabelModel
 */
public class LabelModel extends ChildModel {

	/*private static final String KEY_LABEL = "label";
	private static final String KEY_TEXT = "text";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_ICON = "icon";
	private static final String KEY_DISABLED_ICON = "disabledIcon";
	private static final String KEY_H_ALIGN = "horizontalAlignment";
	private static final String KEY_V_ALIGN = "verticalAlignment";
	private static final String KEY_COMPONENT = "component";
	
	private String label;
	private String text;
	private String image;
	private String icon;
	private String disabledIcon;
	private Integer horizontalAlignment;
	private Integer vertikalAlignment;
	private String component;*/


	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
	/**
			label = data.getString(KEY_LABEL);
		text = data.getString(KEY_TEXT);
		image = data.getString(KEY_IMAGE);
		icon = data.getString(KEY_ICON);
		disabledIcon = data.getString(KEY_DISABLED_ICON);
		horizontalAlignment = optInteger(KEY_H_ALIGN);
		vertikalAlignment = optInteger(KEY_V_ALIGN);
		component = data.getString(KEY_COMPONENT);**/
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();
		/**
		data.put(KEY_LABEL, label);
		data.put(KEY_TEXT, text);
		data.put(KEY_IMAGE, image);
		data.put(KEY_ICON, icon);
		data.put(KEY_DISABLED_ICON, disabledIcon);
		data.put(KEY_H_ALIGN, horizontalAlignment);
		data.put(KEY_V_ALIGN, vertikalAlignment);
		data.put(KEY_COMPONENT, component);**/
		return data;
	}

	@Override
	public String getType() {
		return ModelTypes.LABEL;
	}
	/*
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIcon() {
		return icon;
	}
	
	*//**
	 * Setzt das Icon was bei aktiviertem Label angezeigt wird
	 * 
	 * @param icon Path to the Iconfile (JPG,PNG or GIF)
	 *//*
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDisabledIcon() {
		return disabledIcon;
	}
	
	*//**
	 * Setzt das Icon was angezeigt wird, wenn das Label disabled ist
	 * 
	 * @param disabledIcon Path to the Iconfile (JPG,PNG or GIF)
	 *//*
	public void setDisabledIcon(String disabledIcon) {
		this.disabledIcon = disabledIcon;
	}

	public Integer getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(Integer horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public Integer getVertikalAlignment() {
		return vertikalAlignment;
	}

	public void setVertikalAlignment(Integer vertikalAlignment) {
		this.vertikalAlignment = vertikalAlignment;
	}
	*/
}
