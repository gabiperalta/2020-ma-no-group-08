package dominio.cuentasUsuarios.Roles;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="roles")
public class Rol {
	@Id @GeneratedValue
	private int id;

	private String nombre;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="nombre_rol")
	private List<Privilegio> privilegios;

	public Rol(){}

	public Rol(String unNombre, ArrayList<Privilegio> unosPrivilegios) {
		nombre = unNombre;
		privilegios = unosPrivilegios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrivilegios(List<Privilegio> privilegios) {
		this.privilegios = privilegios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Privilegio> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(ArrayList<Privilegio> privilegios) {
		this.privilegios = privilegios;
	}
	
	public boolean esElRol(String nombreRol) {
		return nombre.equals(nombreRol);
	}
	
	public boolean tieneElPrivilegio(String nombrePrivilegio) {
		return privilegios.stream().anyMatch(privilegio -> privilegio.esElPrivilegio(nombrePrivilegio));
	}
	
}
