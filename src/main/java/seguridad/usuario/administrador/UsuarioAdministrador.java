package seguridad.usuario.administrador;

import seguridad.usuario.Usuario;
import seguridad.usuario.estandar.UsuarioEstandar;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import java.security.SecureRandom;

public class UsuarioAdministrador extends Usuario{

	public UsuarioAdministrador(String unUserName, String unaPassword)  {
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

	public void restaurarContrase�a( String unNombreUsuario ) {
		// TODO		
		RepositorioUsuarios.getInstance().cambiarContrase�a(unNombreUsuario, this.generarContrasenia());
		
	}
	
	public void altaUsuarioColaborador(String unNombreUsuario) {
		
		String unaContrasenia = this.generarContrasenia();
		
		UsuarioEstandar nuevoUsuario = new UsuarioEstandar(unNombreUsuario, unaContrasenia);
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(nuevoUsuario);
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrase�a) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContrase�a); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrase�a) {
		// TODO
	}
	
	public String generarContrasenia() {
		
		int longitudContrasenia = 14;
		byte[] arrayAux = new byte[longitudContrasenia];
		
		SecureRandom secureRandom = new SecureRandom();
		
		secureRandom.nextBytes(arrayAux);
		
		String generatedString = new String(arrayAux);

		// Esta contrase�a autogenerada deberia ser enviada al usuario final de forma segura
		
		return generatedString;
	}
	
}
