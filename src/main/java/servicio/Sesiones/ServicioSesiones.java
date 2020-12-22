/*package servicio.Sesiones;

import java.util.UUID;

import dominio.cuentasUsuarios.CuentaUsuario;
import seguridad.HashPassword;
import seguridad.ValidadorContrasenia;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abOperaciones.ServicioABOperaciones;
import servicio.ab_licitaciones.ServicioABLicitaciones;
import servicio.ab_organizaciones.ServicioABOrganizaciones;
import servicio.abm_entidades.ServicioABMEntidadesBase;
import servicio.abm_entidades.ServicioABMEntidadesJuridicas;
import servicio.abm_usuarios.ServicioABMUsuarios;
import datos.RepositorioSesiones;
import datos.RepositorioUsuarios;

public class ServicioSesiones {

	private UUID identificadorSesion;
	
	
	public void logIn(String nombreUsuario, String contrasenia) throws CredencialesNoValidasException {
		CuentaUsuario cuentaDeUsuario = RepositorioUsuarios.getInstance().buscarUsuario(nombreUsuario);
		
		if(!cuentaDeUsuario.estaBloqueada()) {
			if(cuentaDeUsuario.verificarContrasenia(contrasenia)) { 
				identificadorSesion = RepositorioSesiones.getInstance().logInSesion(cuentaDeUsuario);
			}
			else {
				throw new CredencialesNoValidasException();
			}
		}
		else {
			throw new CredencialesNoValidasException("Esta cuenta esta bloqueada");
		}
	}
	
	public void cambiarContrasenia(String contrasenia) throws Exception{
		CuentaUsuario cuentaDeUsuario = RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).getCuentaUsuario();
		ValidadorContrasenia validador = new ValidadorContrasenia();
		if (validador.esContraseniaValida(contrasenia,cuentaDeUsuario.getContraseniasPrevias())) {
			cuentaDeUsuario.actualizarContrasenia(contrasenia,HashPassword.calcular(contrasenia));
		} else {
			throw new CredencialesNoValidasException("la contrasenia no es valida"); // temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException("la contrasenia no es valida")
		}																			 // TODO si no funciona, usar la de arriba
	}
	
	public void logOut() {
		RepositorioSesiones.getInstance().logOutSesion(identificadorSesion);
	}
	
	public ServicioABOrganizaciones abOrganizacion() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abOrganizaciones();
	}
	
	public ServicioABMUsuarios abmUsuarios() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abmUsuarios();
	}
	public ServicioABMEntidadesJuridicas abmEntidadesJuridicas() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abmEntidadesJuridicas();
	}
	public ServicioABMEntidadesBase abmEntidadesBase() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abmEntidadesBase();
	}
	public ServicioABOperaciones abOperacion() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abOperacion();
	}
	public ServicioABLicitaciones abLicitaciones() throws PermisoDenegadoException {
		return RepositorioSesiones.getInstance().buscarSesion(identificadorSesion).abLicitaciones();
	}
}*/
