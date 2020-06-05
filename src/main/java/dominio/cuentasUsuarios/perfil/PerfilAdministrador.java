package dominio.cuentasUsuarios.perfil;

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
	public String getNombre() {
		return nombre;
	}

	@Override
	public Organizacion getOrganizacion() {
		return null;
	}

}
