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

	public void restaurarContrase�a( /* Organizacion */ ) {
		// TODO
	}
	
	public void altaUsuarioColaborador(String unNombreUsuario, String unaContrase�a) {
		UsuarioEstandar nuevoUsuario = new UsuarioEstandar(unNombreUsuario, unaContrase�a);
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(nuevoUsuario);
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrase�a) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContrase�a); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrase�a) {
		// TODO
	}
}
