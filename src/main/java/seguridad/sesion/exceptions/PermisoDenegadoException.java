package seguridad.sesion.exceptions;

public class PermisoDenegadoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermisoDenegadoException() {
	}

	public PermisoDenegadoException(String message) {
		super(message);
	}

	public PermisoDenegadoException(Throwable cause) {
		super(cause);
	}

	public PermisoDenegadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermisoDenegadoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
