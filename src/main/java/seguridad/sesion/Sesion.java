package seguridad.sesion;

import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.usuario.Usuario;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

public class Sesion {

	private Usuario cuentaDeUsuario;
	
	public Sesion() {
	}

	public void logIn(String nombreUsuario, String contrasenia) throws CredencialesNoValidasException {
		cuentaDeUsuario = RepositorioUsuarios.getInstance().buscarUsuario(nombreUsuario);
		
		if(!cuentaDeUsuario.verificarContrasenia(contrasenia)) { // la cuenta de usuario determina si la contraseña esta bien, y si no lo
			cuentaDeUsuario = null; 							 // esta, disminuye la cantidad de intentnos pendientes
			throw new CredencialesNoValidasException();
		}
	}
	
	public void logOut() {
		cuentaDeUsuario = null;
	}
	
	
}



