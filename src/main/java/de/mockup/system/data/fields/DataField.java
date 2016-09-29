package de.mockup.system.data.fields;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.BaseModel;

import java.util.List;

/**
 *
 */
public abstract class DataField<T> extends BaseModel {

	private String varName;
	private String label;
	private T defaultValue;

	public String getVarName() {
		return varName;
	}

	public abstract List<T> getData(int count);

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		//TODO
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		return super.toConfig();
	}
}
