package de.mockup.system.data.fields;

import de.mockup.system.data.generator.IntegerGenerator;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONArray;
import de.mockup.system.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom DataFields for not generated data
 */
public class CustomDataField extends DataField<String> {

	private static final String KEY_DATA = "data";

	private List<String> customData;

	@Override
	public List<String> getData(int count) {
		List<String> data = new ArrayList<>(count);
		IntegerGenerator intGenerator = new IntegerGenerator();
		intGenerator.setMinValue(0);
		intGenerator.setMaxValue(count - 1);
		List<Integer> indices = intGenerator.generate(count);
		for (Integer index : indices) {
			data.add(customData.get(index));
		}
		return data;
	}

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		super.fromConfig(data);
		JSONArray jsonData = data.optJSONArray(KEY_TYPE);
		if (jsonData != null && jsonData.length() > 0) {
			//TODO Modifiy JSONArray for direkt list access
			List<String> customData = new ArrayList<>(jsonData.length());
			for (int i = 0; i < jsonData.length(); i++) {
				customData.add(jsonData.getString(i));
			}
			this.customData = customData;
		}
	}

	@Override
	public JSONObject toConfig() throws SystemException {
		JSONObject config = super.toConfig();
		config.put(KEY_DATA, customData);
		return config;
	}
}
