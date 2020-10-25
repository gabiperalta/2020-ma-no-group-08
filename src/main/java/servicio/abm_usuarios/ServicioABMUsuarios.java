package servicio.abm_usuarios;


import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import datos.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;
import temporal.seguridad.repositorioUsuarios.exceptions.RolInvalidoException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

import javax.persistence.EntityManager;

public class ServicioABMUsuarios {

    RepositorioUsuarios repositorioUsuarios;

    public ServicioABMUsuarios(EntityManager entityManager){ repositorioUsuarios = new RepositorioUsuarios(entityManager); }

	public void blanquearContrasenia( String unNombreUsuario ) throws Exception {
		// TODO

		CuentaUsuario unUsuario = repositorioUsuarios.buscarUsuario(unNombreUsuario);
		
		unUsuario.blanquearContrasenia();

	}
	
	public void altaUsuarioColaborador(String unNombreUsuario, Organizacion organizacion, ArrayList<String> rolesAsignados) throws UsuarioYaExistenteException, RolInvalidoException{
		
		if(!repositorioUsuarios.existeElUsuario(unNombreUsuario)) {
			if(!rolesAsignados.stream().anyMatch(nombreRol -> nombreRol.equals("ROL_ADMINISTRADOR_SISTEMA"))) {
				repositorioUsuarios.agregarUsuarioEstandar( unNombreUsuario, organizacion, rolesAsignados); // el usuario se agrega a si mismo al repo de usuarios
			}
			else {
				throw new RolInvalidoException("Rol asignado no valido");
			}
		}
		else {
			throw new UsuarioYaExistenteException("Este nombre de usuario ya esta en uso.");
		}
		
	}
	
	public void bajaUsuarioColaborador(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException {
        repositorioUsuarios.eliminarUsuarioEstandar(unNombreUsuario, unaContrasenia); // Para boorar un usuario, debo saber sus credenciales
	}

	public void modificacionUsuarioColaborador(String unNombreUsuario, String unaContrasenia, String nuevoNombreUsuario) throws CredencialesNoValidasException, UsuarioYaExistenteException {
		CuentaUsuario unUsuario = repositorioUsuarios.buscarUsuario(unNombreUsuario);
		if(unUsuario.verificarContrasenia(unaContrasenia)) {
			unUsuario.cambiarNombre(nuevoNombreUsuario, repositorioUsuarios);
		}
		else {
			throw new CredencialesNoValidasException();
		}
	}
}
