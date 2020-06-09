package dominio.excepciones;

public class LaCompraNoRequierePresupuestosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LaCompraNoRequierePresupuestosException(String mensaje) {
		super(mensaje);
	}
}
