package de.mockup.system.model.components.textfields;

/**
 * Textfield
 */

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

public class TextFieldModel extends ChildModel {

	/*private static final String KEY_LABEL = "label";
	private static final String KEY_TEXT = "text";
	private static final String KEY_DOC = "doc";
	private static final String KEY_COLUMNS = "columns";
	private static final String KEY_FONT = "font";
	private static final String KEY_HORIZONTALALIGNMENT = "horizontalAlignment";
	private static final String KEY_SCROLLOFFSET ="scrolloffset";

	private String label;
	private String text;
	private String doc;
	private Integer columns;
	private String font;
	private Integer horizontalAlignment;
	private Integer scrolloffset;*/

	@Override
	public String getType() {
		return ModelTypes.Fields.TEXT_FIELD;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		/*label = data.getString(KEY_LABEL);
		text = data.optString(KEY_TEXT);
		doc = data.optString(KEY_DOC);
		columns = optInteger(KEY_COLUMNS);
		font = data.getString(KEY_FONT);
		horizontalAlignment =  optInteger(KEY_HORIZONTALALIGNMENT);
		scrolloffset = optInteger(KEY_SCROLLOFFSET);*/
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject data = super.toConfig();
		/*data.put(KEY_LABEL, label);
		data.put(KEY_TEXT, text);
		data.put(KEY_DOC, doc);
		data.put(KEY_COLUMNS, columns);
		data.put(KEY_FONT, columns);
		data.put(KEY_HORIZONTALALIGNMENT, horizontalAlignment);
		data.put(KEY_SCROLLOFFSET, scrolloffset);*/
		return data;
	}

	/*
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Integer getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(Integer horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public Integer getScrolloffset() {
		return scrolloffset;
	}

	public void setScrolloffset(Integer scrolloffset) {
		this.scrolloffset = scrolloffset;
	}*/
}
