package seguridad.usuario.administrador;

import seguridad.usuario.Usuario;
import seguridad.usuario.estandar.UsuarioEstandar;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

public class UsuarioAdministrador extends Usuario{

	public UsuarioAdministrador(String unUserName, String unaPassword) {
		super(unUserName, unaPassword);
	}
	
	@Override
	public boolean esAdministrador() {
		return true;
	}
	
	public void altaOrganizacion() {
		// TODO
	}
	
	public void bajaOrganizavion() {
		// TODO
	}
	
	public void modificacionOrganizavion() {
		// TODO
	}

	public void restaurarContraseña( /* Organizacion */ ) {
		// TODO
	}
	
	public void altaUsuarioColaborador(String unNombreUsuario, String unaContraseña) {
		UsuarioEstandar nuevoUsuario = new UsuarioEstandar(unNombreUsuario, unaContraseña);
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(nuevoUsuario);
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContraseña) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContraseña); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContraseña) {
		// TODO
	}
}
