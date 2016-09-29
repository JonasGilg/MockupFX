package de.mockup.system.service.impl;

import de.mockup.system.SystemBundle;
import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONArray;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.Project;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.system.service.ProjectService;
import de.mockup.system.service.internal.FileSystemService;
import de.mockup.system.util.ModelUtil;
import de.mockup.system.util.ZipUtil;
import de.mockup.ui.gui.windows.dialogs.stringeditor.StringEditorController;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PersistenceService for Projects
 */
public class ProjectServiceImpl extends PersistenceServiceBaseImpl implements ProjectService {

    public static final String FILE_EXTENSION = ".mockup";

    @Override
    public void saveProject(AppConfig appConfig, Project project) throws SystemException {
        ModelUtil modelUtil = SystemBundle.getService(ModelUtil.class);
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);

        File projectDir = fileSystemService.getProjectDir(appConfig.getAppFolder(), project.getName());
        File projectFile = fileSystemService.getProjectFile(projectDir, false);

        System.out.println("PDIR: " + projectDir.getAbsolutePath());
        String storagePath = processStoragePath(project.getStoragePath(), project.getName());
        project.setStoragePath(storagePath);

        System.out.println("STORE TO: " + storagePath);

        Map<Integer, ViewModel> viewStorage = project.getViewStorage();

        JSONObject config = project.toConfig();
        config.put("views", modelUtil.createViewStorageJson(viewStorage));

        try {
            FileUtils.writeStringToFile(projectFile, config.toString());

            System.out.println("Saving project...");
            System.out.println(projectFile.getAbsolutePath());

        } catch (IOException e) {
            throw new SystemException(SystemErrorCodes.ENTITY_WRITE_ERROR);
        }

        //Save dirty Views
        for (ViewModel view : viewStorage.values()) {
            System.out.println("Save view" + view.getTitle() + " " + view.isDirty());
            //	if (view.isDirty()) {
            this.saveComponent(projectDir, project, view);
            //		}
        }
        //TODO System.out.println("Saving Internationalized Strings...");
        //TODO fails tests StringEditorController.getInstance().save(projectDir);
        
        ZipUtil.createZip(projectDir.getPath(),
                storagePath);
    }

    private String processStoragePath(String path, String name) {
        File target = new File(path);
        if (!path.endsWith(FILE_EXTENSION)) {
            if (target.isDirectory()) {
                return path + File.separator +  name + FILE_EXTENSION;
            }
            return path + FILE_EXTENSION;
        }
        return path;
    }

    @Override
    public Project getProject(AppConfig appConfig, Project project) throws SystemException {

        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File projectDir = fileSystemService.getProjectDir(appConfig.getAppFolder(),
                FilenameUtils.getBaseName(project.getStoragePath()));

        ZipUtil.extractZip(project.getStoragePath(), projectDir.getPath());

        File projectFile = fileSystemService.getProjectFile(projectDir, true);

        try {

            JSONObject config = new JSONObject(FileUtils.readFileToString(projectFile));
            project.fromConfig(config);

            Integer rootViewKey = config.optInteger("rootView");

            JSONArray views = config.getJSONArray("views");
            for (int i = 0; i < views.length(); i++) {

                JSONObject view = views.getJSONObject(i);

                File viewFile = fileSystemService.getComponentFile(projectDir, view.getString(ViewModel.KEY_STORAGE), true);

                ViewModel viewModel = new ViewModel();
                loadModel(viewFile, viewModel);
                project.addView(viewModel);

                if (rootViewKey != null && viewModel.getId().equals(rootViewKey)) {
                    project.setRootView(viewModel);
                }
            }

            //TODO fails tests StringEditorController.getInstance().load(projectDir);

            return project;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(SystemErrorCodes.ENTITY_UNPROCESSABLE);
        }
    }


    private void saveComponent(File projectDir, Project project, ViewModel view) throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File componentFile = fileSystemService.getComponentFile(projectDir, view.getStorageName(), false);

        System.out.println("Saving view " + view.getTitle() + " ...");
        System.out.println(componentFile.getAbsolutePath());

        saveModel(componentFile, view);
    }

    @Override
    public List<Project> getProjects(AppConfig appConfig) throws SystemException {
        FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
        File[] projectsFiles = fileSystemService.getProjectDirs(appConfig.getAppFolder());
        List<Project> models = new ArrayList<>(projectsFiles.length);
        for (File file : projectsFiles) {
            File projectFile = fileSystemService.getProjectFile(file, false);
            Project project = new Project();
            loadModel(projectFile, project);
            models.add(project);
        }
        return models;
    }
}
