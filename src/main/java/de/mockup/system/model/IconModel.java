package de.mockup.system.model;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;

import java.util.List;

public class IconModel extends BaseModel {

    public static final int SYSTEM_TYPE = 1;
    public static final int USER_TYPE = 2;

    private static final String KEY_NAME = "name";
    private static final String ICON_PATH = "path";
    private static final String TAG_LIST = "tags";
    private static final String CATEGORY ="category";

    private int type;
    private String name;
    private String path;
    private List<String> tags;
    private String category;


    @Override
    public JSONObject toConfig() throws SystemException {
        JSONObject config = super.toConfig();
        config.put(KEY_NAME, name);
        config.put(ICON_PATH,path);
        config.put(TAG_LIST,tags);
        config.put(CATEGORY, category);
        return config;
    }

    @Override
    public void fromConfig(JSONObject data) throws SystemException {
        this.name = data.optString(KEY_NAME);
        this.path = data.getString(ICON_PATH);
        this.tags = data.getJSONArray(TAG_LIST).toList(String.class);
        this.category = data.optString(CATEGORY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(String tag) {
        this.tags.add(tag);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
