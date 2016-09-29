package de.mockup.system.service.internal;

import de.mockup.system.exceptions.SystemException;

import java.io.File;

public interface FileSystemService {

	/**
	 * Returns the Projectdirectory as File.
	 * If directory not exists and force is true throws {@link SystemException} else
	 * Directory is autocreated.
	 *
	 * @param path  Projectdirectory to be represented as file.
	 * @param force If true and directory does not exists, activates SystemException, else directory is created.
	 * @return Filedescriptor
	 * @throws SystemException
	 */
	@Deprecated
	File getProjectDir(String path, boolean force) throws SystemException;


	/**
	 * XXX REDESIGN
	 **/

	File getTemplateDir() throws SystemException;

	File[] getTemplateFiles() throws SystemException;

	File getComponentFile(File projectDir, String name, boolean force) throws SystemException;

	File getProjectFile(File projectDir, boolean force) throws SystemException;

	File getConfigFile() throws SystemException;

	File getStatusFile() throws SystemException;

	File[] getProjectDirs(String applicationFolder) throws SystemException;

	File getProjectDir(String applicationFolder, String projectName) throws SystemException;

    File getIconDirectory() throws SystemException;
}
