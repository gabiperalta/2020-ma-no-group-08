package temporal.seguridad.repositorioUsuarios.exceptions;

public class UsuarioYaExistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsuarioYaExistenteException() {
	}

	public UsuarioYaExistenteException(String message) {
		super(message);
	}

	public UsuarioYaExistenteException(Throwable cause) {
		super(cause);
	}

	public UsuarioYaExistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioYaExistenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
