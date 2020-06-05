package dominio.cuentasUsuarios.perfil;

public interface Perfil {
	public boolean esUsuarioAdministrador();
	public String getNombre();
	public Organizacion getOrganizacion();
}
