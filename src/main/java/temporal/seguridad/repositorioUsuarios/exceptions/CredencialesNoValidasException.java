package temporal.seguridad.repositorioUsuarios.exceptions;

public class CredencialesNoValidasException extends Exception {

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
