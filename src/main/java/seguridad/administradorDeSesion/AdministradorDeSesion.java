package seguridad.administradorDeSesion;

import dominio.cuentasUsuarios.CuentaUsuario;
import seguridad.HashPassword;
import seguridad.sesion.Sesion;
import seguridad.sesion.SesionAdministrador;
import seguridad.sesion.SesionEstandar;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.ValidadorContrasenia;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

public class AdministradorDeSesion {
	private Sesion sesion;
	public AdministradorDeSesion() {
	}

	public void logIn(String nombreUsuario, String contrasenia) throws CredencialesNoValidasException {
		
		CuentaUsuario cuentaDeUsuario = RepositorioUsuarios.getInstance().buscarUsuario(nombreUsuario);
		
		if(!cuentaDeUsuario.estaBloqueada()) {
			if(cuentaDeUsuario.verificarContrasenia(contrasenia)) { // la cuenta de usuario determina si la contrasenia esta bien, y si no lo
				if(cuentaDeUsuario.esAdministrador()) {				// esta, disminuye la cantidad de intentnos pendientes
					sesion = new SesionAdministrador(cuentaDeUsuario.getUserName());
				}
				else {
					sesion = new SesionEstandar(cuentaDeUsuario.getUserName(), cuentaDeUsuario.getOrganizacion()); // TODO Modelar organizacion
				}
			}
			else {
				throw new CredencialesNoValidasException();
			}
		}
		else {
			// TODO, Tirar error cuenta bloqueada
		}
		
	}
	
	public void logOut() {
		sesion = null;
	}

	public void cambiarContrasenia(String contrasenia) throws Exception{
		CuentaUsuario cuentaDeUsuario = RepositorioUsuarios.getInstance().buscarUsuario(sesion.getNombre());
		ValidadorContrasenia validador = new ValidadorContrasenia();
		if (validador.esContraseniaValida(contrasenia,cuentaDeUsuario.getContraseniasPrevias())) {
			cuentaDeUsuario.actualizarContrasenia(contrasenia,HashPassword.calcular(contrasenia));
		} else {
			throw new CredencialesNoValidasException("la contrasenia no es valida"); // temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException("la contrasenia no es valida")
		}																			 // TODO si no funciona, usar la de arriba
	}
	
	public Sesion getSesion() {
		return sesion;
	}
}