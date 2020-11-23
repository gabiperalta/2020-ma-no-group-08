package dominio.cuentasUsuarios.Roles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "privilegios")
public class Privilegio {
	@Id @GeneratedValue
	private String id;

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Privilegio(){}

	public Privilegio(String unNombre) {
		nombre = unNombre;
	}
	
	public boolean esElPrivilegio(String unNombrePrivilegio) {
		return nombre.equals(unNombrePrivilegio);
	}
}
