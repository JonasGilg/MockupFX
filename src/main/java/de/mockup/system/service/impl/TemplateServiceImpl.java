package de.mockup.system.service.impl;

import de.mockup.system.SystemBundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.TemplateModel;
import de.mockup.system.service.TemplateService;
import de.mockup.system.service.internal.FileSystemService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation to persist TemplateModels
 */
public class TemplateServiceImpl extends PersistenceServiceBaseImpl implements TemplateService {

    int maxTemplateId = 0;

    @Override
    public List<TemplateModel> getTemplates() throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File[] templateFiles = fileSystemService.getTemplateFiles();
        System.out.println("templateFiles: " + templateFiles.length);
        List<TemplateModel> models = new ArrayList<>(templateFiles.length);
        for (File file : templateFiles) {
            TemplateModel model = new TemplateModel();
            loadModel(file, model);
            models.add(model);
            if (model.getId() > maxTemplateId) {
                maxTemplateId = model.getId();
            }
        }
        return models;
    }

    @Override
    public void saveTemplate(TemplateModel template) throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File templateDir = fileSystemService.getTemplateDir();

        template.setId(maxTemplateId++);
        File componentFile = fileSystemService.getComponentFile(templateDir, Integer.toString(template.getId()), false);

        System.out.println("Saving template " + template.getName() + " ...");
        saveModel(componentFile, template);
    }

    @Override
    public TemplateModel findById(int id) throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File templateDir = fileSystemService.getTemplateDir();
        File componentFile = fileSystemService.getComponentFile(templateDir, Integer.toString(id), false);

        TemplateModel model = new TemplateModel();
        loadModel(componentFile, model);
        return model;
    }

}
