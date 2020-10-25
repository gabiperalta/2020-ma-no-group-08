package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PerfilAdministrador extends Perfil {

	public PerfilAdministrador(){}

	public PerfilAdministrador(String nombre){
		super(nombre);
	}

	@Override
	public boolean esUsuarioAdministrador() {
		return true;
	}

	@Override
	public Organizacion getOrganizacion() {
		return null;
	}

}
