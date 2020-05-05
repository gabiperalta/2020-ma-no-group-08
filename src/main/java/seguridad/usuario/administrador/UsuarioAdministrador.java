package seguridad.usuario.administrador;

import seguridad.HashPassword;
import seguridad.usuario.Usuario;
import seguridad.usuario.estandar.UsuarioEstandar;
import seguridad.ValidadorContrasenia;
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

	public void blanquearContrasenia( String unNombreUsuario ) throws Exception {
		// TODO

		Usuario usuario = RepositorioUsuarios.getInstance().buscarUsuario(userName);
		ValidadorContrasenia validador = new ValidadorContrasenia();
		String contrasenia = this.generarContrasenia();

		if(validador.esContraseniaValida(contrasenia, usuario.getContraseniasPrevias())) {
			Usuario unUsuario = RepositorioUsuarios.getInstance().buscarUsuario(unNombreUsuario);
			unUsuario.actualizarContrasenia(contrasenia, HashPassword.calcular(contrasenia));
		} else {
			throw new CredencialesNoValidasException("la contrasenia no es valida");
		}

	}
	
	public void altaUsuarioColaborador(String unNombreUsuario) {
		
		String unaContrasenia = this.generarContrasenia();
		
		UsuarioEstandar nuevoUsuario = new UsuarioEstandar(unNombreUsuario, unaContrasenia);
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(nuevoUsuario);
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContrasenia); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrasenia) {
		// TODO
	}
	
	public String generarContrasenia() {
		
		int longitudContrasenia = 14;
		byte[] arrayAux = new byte[longitudContrasenia];
		
		SecureRandom secureRandom = new SecureRandom();
		
		secureRandom.nextBytes(arrayAux);
		
		String generatedString = new String(arrayAux);

		// Esta contraseña autogenerada deberia ser enviada al usuario final de forma segura
		
		return generatedString;
	}
	
}
