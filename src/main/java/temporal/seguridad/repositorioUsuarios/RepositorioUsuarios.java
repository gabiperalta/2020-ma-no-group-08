package temporal.seguridad.repositorioUsuarios;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

// SINGLETON
public class RepositorioUsuarios {
	
	private ArrayList<CuentaUsuario> usuarios;
	
	
	private static class RepositorioUsuariosHolder {		
        static final RepositorioUsuarios singleInstanceRepositorioUsuarios = new RepositorioUsuarios();
    }
	
	public static RepositorioUsuarios getInstance() {
        return RepositorioUsuariosHolder.singleInstanceRepositorioUsuarios;
    }
	
	public RepositorioUsuarios() {
		
		CuentaUsuario administrador1 = new CuentaUsuario("admin1", "1234");
		CuentaUsuario administrador2 = new CuentaUsuario("admin2", "1234");
		CuentaUsuario administrador3 = new CuentaUsuario("admin3", "1234");
		usuarios = new ArrayList<CuentaUsuario>();
		
		usuarios.add(administrador1);
		usuarios.add(administrador2);
		usuarios.add(administrador3); // Por defecto se crea con estos 3 admins
		
	}

	public boolean existeElUsuario(String unNombreUsuario) {
		boolean existiaElUsuario;
		try {
			this.buscarUsuario(unNombreUsuario);
			existiaElUsuario = true;
		}
		catch (Exception NoSuchElementException){
			existiaElUsuario = false;
		}
		return existiaElUsuario;
	}
	
	public void agregarUsuarioEstandar(CuentaUsuario unUsuarioEstandar) {
		usuarios.add(unUsuarioEstandar);
	}
	
	public void eliminarUsuarioEstandar(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException{
		CuentaUsuario usuarioABorrar = this.buscarUsuario(unNombreUsuario);
		if(usuarioABorrar.verificarContrasenia(unaContrasenia)) {
			usuarios.remove(usuarioABorrar);
		}
		else {
			throw new CredencialesNoValidasException();
		}
	}
	
	public CuentaUsuario buscarUsuario(String unUsername) {
		CuentaUsuario unUsuario = usuarios.stream().filter(usuario -> usuario.getUserName().equals(unUsername)).findFirst().get();
		// TODO si el usuario no existe, debemos tirar la excepcion UsuarioNoExisteException? o la con que tira directamente el get ya alcanza?
		return unUsuario;
	}

}