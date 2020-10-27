package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "perfiles")
public abstract class Perfil {
	@Id
	private String nombre;

	public Perfil(){}

	public Perfil(String unNombre){
		nombre = unNombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public abstract boolean esUsuarioAdministrador();
	public abstract Organizacion getOrganizacion();
}
