package de.mockup.system.model.components;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;

/**
 * .
 */
public class FileChooserModel extends ChildModel {
    @Override
    public JSONObject toConfig() throws SystemException {
        return super.toConfig();
    }

    @Override
    public void fromConfig(JSONObject data) throws SystemException {
        super.fromConfig(data);
    }
    @Override
    public String getType() {
        return ModelTypes.FILE_CHOOSER;
    }
}
