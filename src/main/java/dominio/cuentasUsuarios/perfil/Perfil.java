package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;

public interface Perfil {
	public boolean esUsuarioAdministrador();
	public String getNombre();
	public Organizacion getOrganizacion();
}
