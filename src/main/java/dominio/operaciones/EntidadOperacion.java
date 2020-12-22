package dominio.operaciones;

import javax.persistence.*;

@Entity
public class EntidadOperacion {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String nombre;
	private String cuil;
	private String direccion;

	public EntidadOperacion(String nombre, String cuil, String direccion) {
		this.nombre = nombre;
		this.cuil = cuil;
		this.direccion = direccion;
	}

	public EntidadOperacion() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
