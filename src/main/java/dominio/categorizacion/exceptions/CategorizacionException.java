package dominio.categorizacion.exceptions;

public class CategorizacionException extends Exception {
	private static final long serialVersionUID = 1L;

	public CategorizacionException() {
	}

	public CategorizacionException(String message) {
		super(message);
	}

	public CategorizacionException(Throwable cause) {
		super(cause);
	}

	public CategorizacionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategorizacionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
