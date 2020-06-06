package servicio.abm_usuarios;

import java.security.SecureRandom;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.perfil.PerfilEstandar;
import dominio.entidades.Organizacion;
import seguridad.HashPassword;
import seguridad.ValidadorContrasenia;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

public class ServicioABMUsuarios {
	public void blanquearContrasenia( String unNombreUsuario ) throws Exception {
		// TODO

		CuentaUsuario unUsuario = RepositorioUsuarios.getInstance().buscarUsuario(unNombreUsuario);
		ValidadorContrasenia validador = new ValidadorContrasenia();
		String contrasenia = this.generarContrasenia();

		if(validador.esContraseniaValida(contrasenia, unUsuario.getContraseniasPrevias())) {
			unUsuario.actualizarContrasenia(contrasenia, HashPassword.calcular(contrasenia));
		} else {
			throw new CredencialesNoValidasException("la contrasenia no es valida");
		}

	}
	
	public void altaUsuarioColaborador(String unNombreUsuario, Organizacion organizacion) {
		
		String unaContrasenia = this.generarContrasenia(); // TODO, Debo ver donde verifico que la organizacion exista
		
		PerfilEstandar nuevoPerfil = new PerfilEstandar(unNombreUsuario, organizacion);
		CuentaUsuario nuevoUsuario = new CuentaUsuario( nuevoPerfil, unaContrasenia);
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(nuevoUsuario);
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContrasenia); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrasenia) {
		// TODO
	}
	
	private String generarContrasenia() {
		
		int longitudContrasenia = 14;
		byte[] arrayAux = new byte[longitudContrasenia];
		
		SecureRandom secureRandom = new SecureRandom();
		
		secureRandom.nextBytes(arrayAux);
		
		String generatedString = new String(arrayAux);

		// Esta contraseña autogenerada deberia ser enviada al usuario final de forma segura
		
		return generatedString;
	}
}
