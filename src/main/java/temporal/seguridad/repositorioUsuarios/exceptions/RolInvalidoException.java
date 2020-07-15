package temporal.seguridad.repositorioUsuarios.exceptions;

public class RolInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RolInvalidoException() {
	}

	public RolInvalidoException(String message) {
		super(message);
	}

	public RolInvalidoException(Throwable cause) {
		super(cause);
	}

	public RolInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public RolInvalidoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
