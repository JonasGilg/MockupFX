package de.mockup.system.model.components.textfields;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.ChildModel;

/**
 * .
 */
public class FormattedTextFieldModel extends ChildModel {
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
        return ModelTypes.Fields.FORMATTED_TEXTFIELD;
    }
}
