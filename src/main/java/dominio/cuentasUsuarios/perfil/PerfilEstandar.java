package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

public class PerfilEstandar implements Perfil{
	
	private String nombre;
	private Organizacion organizacion;

	public PerfilEstandar(String unNombre, Organizacion unaOrganizacion) {
		nombre = unNombre;
		organizacion = unaOrganizacion;
	}
	
	@Override
	public boolean esUsuarioAdministrador() {
		return false;
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
		return organizacion;
	}
	
}
