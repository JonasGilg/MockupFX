package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.components.containers.ContainerModel;

/**
 * TemplateModel storages a Template a of Container or Component.
 */
public class TemplateModel extends ContainerModel {

    private static final String KEY_NAME = "name";

    private String type;

    private String name;

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public JSONObject toConfig() throws SystemException {
        JSONObject config = super.toConfig();
        config.put(KEY_NAME, name);
        config.put(KEY_NAME, type);
        return config;
    }

    @Override
    public void fromConfig(JSONObject data) throws SystemException {
        super.fromConfig(data);
        this.name = data.optString(KEY_NAME);
        this.type = data.getString(KEY_TYPE);
        System.out.println("CREATED: " + name + "_" + type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
