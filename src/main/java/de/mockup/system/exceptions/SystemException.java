package de.mockup.system.exceptions;

/**
 * Exceptions thrown by the system services. Contains statuscode.
 */

public class SystemException extends Throwable {

	private static final long serialVersionUID = 1L;

	private int statusCode;

	public SystemException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
