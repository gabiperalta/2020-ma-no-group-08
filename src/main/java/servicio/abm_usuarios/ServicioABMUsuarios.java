package servicio.abm_usuarios;


import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

public class ServicioABMUsuarios {
	public void blanquearContrasenia( String unNombreUsuario ) throws Exception {
		// TODO

		CuentaUsuario unUsuario = RepositorioUsuarios.getInstance().buscarUsuario(unNombreUsuario);
		
		unUsuario.blanquearContrasenia();

	}
	
	public void altaUsuarioColaborador(String unNombreUsuario, Organizacion organizacion) throws UsuarioYaExistenteException{
		
		if(!RepositorioUsuarios.getInstance().existeElUsuario(unNombreUsuario)) {
			new CuentaUsuario( unNombreUsuario, organizacion ); // el usuario se agrega a si mismo al repo de usuarios
		}
		else {
			throw new UsuarioYaExistenteException("Este nombre de usuario ya esta en uso.");
		}
		
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException {
		RepositorioUsuarios.getInstance().eliminarUsuarioEstandar(unNombreUsuario, unaContrasenia); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrasenia, String nuevoNombreUsuario) throws CredencialesNoValidasException, UsuarioYaExistenteException {
		CuentaUsuario unUsuario = RepositorioUsuarios.getInstance().buscarUsuario(unNombreUsuario);
		if(unUsuario.verificarContrasenia(unaContrasenia)) {
			unUsuario.cambiarNombre(nuevoNombreUsuario);
		}
		else {
			throw new CredencialesNoValidasException();
		}
	}
}
