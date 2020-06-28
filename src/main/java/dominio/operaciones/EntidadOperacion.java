package dominio.operaciones;

public class EntidadOperacion {
	String nombre;
	String cuil;
	String direccion;
	
	public EntidadOperacion() {}
	public EntidadOperacion(String nombre, String cuil, String direccion) {
		this.nombre = nombre;
		this.cuil = cuil;
		this.direccion = direccion;
	}
}
