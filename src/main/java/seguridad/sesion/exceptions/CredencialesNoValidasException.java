package seguridad.sesion.exceptions;

public class CredencialesNoValidasException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CredencialesNoValidasException() {
	}

	public CredencialesNoValidasException(String message) {
		super(message);
	}

	public CredencialesNoValidasException(Throwable cause) {
		super(cause);
	}

	public CredencialesNoValidasException(String message, Throwable cause) {
		super(message, cause);
	}

	public CredencialesNoValidasException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
