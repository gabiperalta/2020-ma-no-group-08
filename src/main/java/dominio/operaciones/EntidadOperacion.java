package dominio.operaciones;

public class EntidadOperacion {
	String nombre;
	String cuil;
	String direccion;

	public EntidadOperacion(String nombre, String cuil, String direccion) {
		this.nombre = nombre;
		this.cuil = cuil;
		this.direccion = direccion;
	}


	public String getNombre() {
		return nombre;
	}
	public String getCuil() {
		return cuil;
	}
	public String getDireccion() {
		return direccion;
	}
	public boolean correspondeEntidad(EntidadOperacion entidad) {
		return this.nombre.equals(entidad.getNombre()) && this.cuil.equals(entidad.getCuil()) && this.direccion.equals(entidad.getDireccion());
	}
}
