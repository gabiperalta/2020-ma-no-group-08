package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

public interface Perfil {
	public boolean esUsuarioAdministrador();
	public void setNombre(String unNombre);
	public String getNombre();
	public Organizacion getOrganizacion();
}
