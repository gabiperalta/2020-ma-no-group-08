package temporal.seguridad.repositorioUsuarios;

import java.util.ArrayList;

import seguridad.usuario.Usuario;
import seguridad.usuario.administrador.UsuarioAdministrador;
import seguridad.usuario.estandar.UsuarioEstandar;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

// SINGLETON
public class RepositorioUsuarios {
	
	private ArrayList<Usuario> usuarios;
	
	
	private static class RepositorioUsuariosHolder {		
        static final RepositorioUsuarios singleInstanceRepositorioUsuarios = new RepositorioUsuarios();
    }
	
	public static RepositorioUsuarios getInstance() {
        return RepositorioUsuariosHolder.singleInstanceRepositorioUsuarios;
    }
	
	public RepositorioUsuarios() {
		UsuarioAdministrador administrador1 = new UsuarioAdministrador("admin1", "1234");
		UsuarioAdministrador administrador2 = new UsuarioAdministrador("admin2", "1234");
		UsuarioAdministrador administrador3 = new UsuarioAdministrador("admin3", "1234");
		usuarios = new ArrayList<Usuario>();
		
		usuarios.add(administrador1);
		usuarios.add(administrador2);
		usuarios.add(administrador3); // Por defecto se crea con estos 3 admins
	}

	
	
	public void agregarUsuarioEstandar(UsuarioEstandar unUsuarioEstandar) {
		usuarios.add(unUsuarioEstandar);
	}
	
	public void eliminarUsuarioEstandar(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException{
		Usuario usuarioABorrar = this.buscarUsuario(unNombreUsuario);
		if(usuarioABorrar.verificarContrasenia(unaContrasenia)) {
			usuarios.remove(usuarioABorrar);
		}
		else {
			throw new CredencialesNoValidasException();
		}
	}
	
	public Usuario buscarUsuario(String unUsername) {
		Usuario unUsuario = usuarios.stream().filter(usuario -> usuario.getUserName().equals(unUsername)).findFirst().get();
		// TODO si el usuario no existe, debemos tirar la excepcion UsuarioNoExisteException? o la con que tira directamente el get ya alcanza?
		return unUsuario;
	}
	
	public void cambiarContrasenia(String unUsername, String nuevaContrasenia) {
		Usuario unUsuario = buscarUsuario(unUsername);
		unUsuario.cambiarContrasenia(nuevaContrasenia);
	}
	
}