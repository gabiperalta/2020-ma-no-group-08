package dominio.operaciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EntidadOperacion {

	@Id
	String nombre;
	String cuil;
	String direccion;

	public EntidadOperacion(String nombre, String cuil, String direccion) {
		this.nombre = nombre;
		this.cuil = cuil;
		this.direccion = direccion;
	}

	public EntidadOperacion() {

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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
