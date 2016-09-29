package de.mockup.system.data;

import de.mockup.system.data.fields.DataField;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dynamic DataModel for a example data record
 */
public class DataModel extends BaseModel {

	private Map<String, Object> dataMap;

	public DataModel(List<DataField> fields) {
		this.dataMap = new HashMap<>(fields.size());
	}

	public DataModel(Map<String, Object> values) {
		this.dataMap = values;
	}

	public <X> X get(DataField<X> field) {
		return (X) this.dataMap.get(field.getVarName());
	}

	public <X> void set(DataField<X> field, X value) {
		this.dataMap.put(field.getVarName(), value);
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
