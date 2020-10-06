package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

public class PerfilAdministrador implements Perfil {

	private String nombre;
	
	public PerfilAdministrador(String unNombre) {
		nombre = unNombre;
	}
	
	@Override
	public boolean esUsuarioAdministrador() {
		return true;
	}
	
	@Override
	public void setNombre(String unNombre) {
		nombre = unNombre;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public Organizacion getOrganizacion() {
		return null;
	}

}
