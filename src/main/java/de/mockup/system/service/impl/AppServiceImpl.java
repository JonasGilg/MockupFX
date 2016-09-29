package de.mockup.system.service.impl;

import de.mockup.system.SystemBundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.AppStatus;
import de.mockup.system.service.AppService;
import de.mockup.system.service.internal.FileSystemService;

import java.io.File;

/**
 * Service to persist the AppConfig and AppStatus
 */
public class AppServiceImpl extends PersistenceServiceBaseImpl implements AppService {

	@Override
	public AppConfig getAppConfig() throws SystemException {
		FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
		File configFile = fileSystemService.getConfigFile();
		AppConfig appConfig = new AppConfig();
		loadModel(configFile, appConfig);
		return appConfig;
	}

	@Override
	public void saveAppConfig(AppConfig config) throws SystemException {
		FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
		File configFile = fileSystemService.getConfigFile();
		saveModel(configFile, config);
	}

	@Override
	public AppStatus getAppStatus() throws SystemException {
		FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
		File statusFile = fileSystemService.getStatusFile();
		AppStatus appConfig = new AppStatus();
		loadModel(statusFile, appConfig);
		return appConfig;
	}

	@Override
	public void saveAppStatus(AppStatus status) throws SystemException {
		FileSystemService fileSystemService = SystemBundle.getService(FileSystemService.class);
		File statusFile = fileSystemService.getStatusFile();
		saveModel(statusFile, status);
	}

}
