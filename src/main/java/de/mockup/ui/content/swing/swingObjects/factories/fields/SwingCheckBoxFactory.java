package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.buttons.CheckboxModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingCheckBox;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

/**
 * .
 */
public class SwingCheckBoxFactory extends SwingComponentFactoryBase<SwingCheckBox, CheckboxModel> {
    @Override
    public SwingCheckBox createComponent(CheckboxModel model) {
        return new SwingCheckBox(model);
    }

    @Override
    public void writeToModel(SwingCheckBox component, CheckboxModel model) throws SystemException {
        writePropertiesToModel(component, model);
    }

    @Override
    public void readFromModel(CheckboxModel model, SwingCheckBox component) throws SystemException {
        readPropertiesFromModel(model, component);
    }
}
