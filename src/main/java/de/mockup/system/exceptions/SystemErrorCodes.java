package de.mockup.system.exceptions;

/**
 * TODO Refactor and group codes.
 */
public class SystemErrorCodes {

	/**
	 * Storage 0-100
	 **/
	public static final int PROJECT_NOT_FOUND = 1;
	public static final int ENTITY_NOT_FOUND = 2;
	public static final int ENTITY_UNPROCESSABLE = 3;
	public static final int ENTITY_WRITE_ERROR = 4;

	public static final int CANNOT_CREATE_MODEL = 5;
	public static final int CANNOT_CREATE_COMPONENT = 6;

	public static final int CANNOT_CREATE_FOLDER = 7;

	public static final int ID_NOT_SET = 8;

	public static final int FILE_EXISTS = 9;
	public static final int FILE_SYSTEM_ERROR = 10;

	/**
	 * UI 100-...
	 */
	public static final int ERROR_CREATING_SCREENSHOT = 100;

}