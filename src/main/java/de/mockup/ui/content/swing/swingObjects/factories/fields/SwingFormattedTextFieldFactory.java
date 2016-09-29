package de.mockup.ui.content.swing.swingObjects.factories.fields;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.textfields.FormattedTextFieldModel;
import de.mockup.ui.content.swing.swingObjects.components.SwingFormattedTextField;
import de.mockup.ui.content.swing.swingObjects.factories.SwingComponentFactoryBase;

/**
 * .
 */
public class SwingFormattedTextFieldFactory extends SwingComponentFactoryBase<SwingFormattedTextField, FormattedTextFieldModel> {
    @Override
    public SwingFormattedTextField createComponent(FormattedTextFieldModel model) {
        return new SwingFormattedTextField(model);
    }

    @Override
    public void writeToModel(SwingFormattedTextField component, FormattedTextFieldModel model) throws SystemException {
        writePropertiesToModel(component, model);
    }

    @Override
    public void readFromModel(FormattedTextFieldModel model, SwingFormattedTextField component) throws SystemException {
        readPropertiesFromModel(model, component);
    }
}
