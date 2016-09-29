package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONArray;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.util.ModelManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseModel {

    protected static final String KEY_TYPE = "type";
    private static final String CREATED_ON = "created_on";
    private static final String MODIFIED_ON = "modified_on";

    private Date createdOn = new Date();
    private Date modifiedOn = new Date();

    /**
     * To config object
     *
     * @return New JsonObject.
     */
    public JSONObject toConfig() throws SystemException {
        JSONObject json = new JSONObject();
        if (createdOn == null) {
            createdOn = new Date();
        }
        modifiedOn = new Date();
        json.put(CREATED_ON, createdOn.getTime());
        json.put(MODIFIED_ON, modifiedOn.getTime());
        return json;
    }

    /**
     * Read properties from {@link JSONObject} config.
     *
     * @param data JSON to generate Objects from.
     * @throws SystemException
     */
    public void fromConfig(JSONObject data) throws SystemException {
        createdOn = new Date(data.getLong(CREATED_ON));
        modifiedOn = new Date(data.getLong(MODIFIED_ON));
    }

    /**
     * Creates {@link JSONArray} from Modellist.
     *
     * @return JSON with model properties.
     */
    protected JSONArray createJsonList(List<? extends BaseComponentModel> models) throws SystemException {
        JSONArray data = new JSONArray(models.size());
        for (BaseModel model : models) {
            data.put(model.toConfig());
        }
        return data;
    }

    protected List<BaseComponentModel> fromJsonList(JSONArray data) throws SystemException {
        if (data == null) {
            return null;
        }
        List<BaseComponentModel> models = new ArrayList<>(data.length());
        for (int i = 0; i < data.length(); i++) {
            JSONObject config = data.getJSONObject(i);
            BaseComponentModel comp = ModelManager.get().createModel(config.getString(KEY_TYPE));
            comp.fromConfig(config);
            models.add(comp);
        }
        return models;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
