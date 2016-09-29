package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.ColorChooserModel;
import de.mockup.ui.content.swing.swingObjects.special.SwingColorChooser;

/**
 * .
 */
public class SwingColorChooserFactory extends SwingComponentFactoryBase<SwingColorChooser, ColorChooserModel> {
    @Override
    public SwingColorChooser createComponent(ColorChooserModel model) {
        return new SwingColorChooser(model);
    }

    @Override
    public void writeToModel(SwingColorChooser component, ColorChooserModel model) throws SystemException {
        writePropertiesToModel(component, model);
    }

    @Override
    public void readFromModel(ColorChooserModel model, SwingColorChooser component) throws SystemException {
        readPropertiesFromModel(model, component);
    }
}
