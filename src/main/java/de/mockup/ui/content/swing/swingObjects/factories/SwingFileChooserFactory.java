package de.mockup.ui.content.swing.swingObjects.factories;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.FileChooserModel;
import de.mockup.ui.content.swing.swingObjects.special.SwingFileChooser;

public class SwingFileChooserFactory extends SwingComponentFactoryBase<SwingFileChooser, FileChooserModel> {
    @Override
    public SwingFileChooser createComponent(FileChooserModel model) {
        return new SwingFileChooser(model);
    }

    @Override
    public void writeToModel(SwingFileChooser component, FileChooserModel model) throws SystemException {
        writePropertiesToModel(component, model);
    }

    @Override
    public void readFromModel(FileChooserModel model, SwingFileChooser component) throws SystemException {
        readPropertiesFromModel(model, component);
    }
}
