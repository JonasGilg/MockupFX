package de.mockup.system.service.impl;

import de.mockup.system.SystemBundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.IconModel;
import de.mockup.system.service.IconLibraryService;
import de.mockup.system.service.internal.FileSystemService;
import org.apache.commons.io.FilenameUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class IconLibraryServiceImpl extends PersistenceServiceBaseImpl implements IconLibraryService {

    @Override
    public List<IconModel> getIcons() throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File iconRoot = fileSystemService.getIconDirectory();

        File[] files = iconRoot.listFiles(pathname ->
                !FilenameUtils.getExtension(pathname.getName()).equals(".json"));

        List<IconModel> iconNames = new LinkedList<>();
        for (File file : files) {
            String baseName = FilenameUtils.getBaseName(file.getName());
            IconModel model = new IconModel();
            model.setType(IconModel.USER_TYPE);
            loadModel(new File(iconRoot, baseName + ".json"), model);
            iconNames.add(model);
        }
        Reflections reflections = new Reflections("iconlibrary", new ResourcesScanner());
        Set<String> properties = reflections.getResources(s -> s.endsWith(".png"));

        for (String icon : properties) {
            IconModel model = new IconModel();
            model.setName(FilenameUtils.getBaseName(icon));
            model.setPath(icon);
            model.setType(IconModel.SYSTEM_TYPE);
            iconNames.add(model);
        }

        return iconNames;
    }

    @Override
    public void storeIcon(String path, IconModel model) throws SystemException {
        try {
            FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
            File iconRoot = fileSystemService.getIconDirectory();

            String name = FilenameUtils.getName(path);
            String baseName = FilenameUtils.getBaseName(name);

            File target = new File(iconRoot, name);
            File targetStorage = new File(iconRoot, baseName + ".json");

            Files.copy(Paths.get(path), new FileOutputStream(target));

            saveModel(targetStorage, model);

        } catch (IOException e) {
            throw new SystemException(0);
        }
    }

}
