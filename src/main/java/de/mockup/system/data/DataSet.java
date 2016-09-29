package de.mockup.system.data;

import de.mockup.system.data.fields.DataField;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.BaseModel;

import java.util.List;

/**
 * ExampleDataSet
 */
public class DataSet extends BaseModel {

	private List<DataField> fields;
	private List<DataModel> models;

	public List<DataField> getFields() {
		return fields;
	}

	public List<DataModel> getModels() {
		return models;
	}

	public void setFields(List<DataField> fields) {
		this.fields = fields;
	}

	public void setModels(List<DataModel> models) {
		this.models = models;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		//TODO
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		//TODO
		return super.toConfig();
	}
}
