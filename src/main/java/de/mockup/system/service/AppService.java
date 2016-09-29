package de.mockup.system.service;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.AppStatus;

/**
 * Service to persist the AppConfig and AppStatus
 */
public interface AppService {

	/**
	 * Loads the AppConfig from the UserHome Dir.
	 *
	 * @return
	 */
	AppConfig getAppConfig() throws SystemException;

	/**
	 * Stores the AppConfig in the UserHome Dir.
	 *
	 * @param config
	 * @throws SystemException
	 */
	void saveAppConfig(AppConfig config) throws SystemException;

	/**
	 * Loads the AppStatus from the UserHome Dir.
	 *
	 * @return
	 */
	AppStatus getAppStatus() throws SystemException;

	/**
	 * Stores the AppStatus in the UserHome Dir.
	 *
	 * @param status
	 * @throws SystemException
	 */
	void saveAppStatus(AppStatus status) throws SystemException;

}
