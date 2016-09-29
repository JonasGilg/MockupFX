package de.mockup.system.service.internal.impl;

import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.service.internal.FileSystemService;

import java.io.File;
import java.io.FilenameFilter;

public class FileSystemServiceImpl implements FileSystemService {

    private static final String SYSTEM_FOLDER = ".mockup";
    private static final String PROJECTS_DIR = "projects";
    private static final String TEMPLATES_DIR = "templates";
    private static final String CONFIG_FILE = "config.json";
    private static final String STATUS_FILE = "status.json";

    private static final String ICONS = "icons";

    private static final String FILE_EXTENSION = ".json";
    private static final String PROJECT_FILE = "project";
    private static final String COMPONENTS = "components";

    private FilenameFilter JSON_FILTER = (dir, name) -> name.endsWith(FILE_EXTENSION);

    @Override
    @Deprecated
    public File getProjectDir(String path, boolean force) throws SystemException {
        File projectPath = new File(path);
        boolean exists = projectPath.exists() && projectPath.isDirectory();
        if (force && !exists) {
            throw new SystemException(SystemErrorCodes.PROJECT_NOT_FOUND);
        } else if (!exists) {
            projectPath.mkdir();
        }
        return projectPath;
    }

    private File getDirectory(String parent, String child) throws SystemException {
        return getDirectory(new File(parent), child);
    }

    private File getDirectory(File parent, String child) throws SystemException {
        File systemFolder = new File(parent, child);
        if (!systemFolder.exists()) {
            if (!systemFolder.mkdirs()) {
                System.out.println("ERROR FOLDER  CREATE: " + systemFolder.getAbsolutePath());
                throw new SystemException(SystemErrorCodes.CANNOT_CREATE_FOLDER);
            }
        }
        return systemFolder;
    }

    private File getSystemDir() throws SystemException {
        String userHome = System.getProperty("user.home");
        return getDirectory(userHome, SYSTEM_FOLDER);
    }

	private File getSystemFile(String file) throws SystemException {
		String userHome = System.getProperty("user.home");
		File systemDirectory = getDirectory(userHome, SYSTEM_FOLDER);
		return new File(systemDirectory, file);
	}

	@Override
	public File getConfigFile() throws SystemException {
		return getSystemFile(CONFIG_FILE);
	}

	@Override
	public File getStatusFile() throws SystemException {
		return getSystemFile(STATUS_FILE);
	}

    @Override
    public File[] getProjectDirs(String applicationFolder) throws SystemException {
        File templateFir = getDirectory(applicationFolder, PROJECTS_DIR);
        return templateFir.listFiles(JSON_FILTER);
    }

	@Override
	public File getTemplateDir() throws SystemException {
		return getDirectory(getSystemDir(), TEMPLATES_DIR);
	}

	@Override
	public File[] getTemplateFiles() throws SystemException {
		File templateFir = getDirectory(getTemplateDir(), COMPONENTS);
		return templateFir.listFiles(JSON_FILTER);
	}

    @Override
    public File getComponentFile(File projectDir, String name, boolean force) throws SystemException {
        File storeDir = new File(projectDir, COMPONENTS);
        if (!storeDir.exists()) {
            storeDir.mkdir();
        }
        File storeFile = new File(storeDir, normalizeFilename(name) + FILE_EXTENSION);
        if (force && (!storeDir.exists() || !storeFile.exists())) {
            throw new SystemException(SystemErrorCodes.ENTITY_NOT_FOUND);
        }
        return storeFile;
    }

    @Override
    public File getProjectFile(File projectDir, boolean force) throws SystemException {
        File storeFile = new File(projectDir, PROJECT_FILE + FILE_EXTENSION);
        if (force && !storeFile.exists()) {
            throw new SystemException(SystemErrorCodes.ENTITY_NOT_FOUND);
        }
        return storeFile;
    }

    @Override
    public File getProjectDir(String applicationFolder, String projectName) throws SystemException {
        File projectsDir = getDirectory(applicationFolder, PROJECTS_DIR);
        return getDirectory(projectsDir, normalizeFilename(projectName));
    }

    private String normalizeFilename(String name) {
        return name.replaceAll("[:\\\\/*\"?|<>']", "_");
    }

    @Override
    public File getIconDirectory() throws SystemException {
        return getDirectory(getSystemDir(), ICONS);
    }

}
