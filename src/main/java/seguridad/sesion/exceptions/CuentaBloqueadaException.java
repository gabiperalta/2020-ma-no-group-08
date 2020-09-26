package seguridad.sesion.exceptions;

public class CuentaBloqueadaException extends Exception {

    private static final long serialVersionUID = 1L;

    public CuentaBloqueadaException() {
    }

    public CuentaBloqueadaException(String message) {
        super(message);
    }

    public CuentaBloqueadaException(Throwable cause) {
        super(cause);
    }

    public CuentaBloqueadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CuentaBloqueadaException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
