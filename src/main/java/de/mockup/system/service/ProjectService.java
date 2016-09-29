package de.mockup.system.service;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.Project;

import java.util.List;

/**
 * Service to persist Projects
 */
public interface ProjectService {

	/**
	 * Stores a project in the application folder.
	 *
	 * @param project
	 * @throws SystemException
	 */
	void saveProject(AppConfig appConfig, Project project) throws SystemException;

	/**
	 * Loads complete Project
	 *
	 * @param project
	 * @return
	 * @throws SystemException
	 */
	Project getProject(AppConfig appConfig, Project project) throws SystemException;

	/**
	 * Loads all Projects from the application folder.
	 * <b>Note: Service do not load views and datasets</b>
	 *
	 * @param appConfig
	 * @return
	 * @throws SystemException
	 */
	List<Project> getProjects(AppConfig appConfig) throws SystemException;

}
