package datos;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.entidades.Organizacion;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import javax.persistence.EntityManager;


public class RepositorioUsuarios {

	//private ArrayList<CuentaUsuario> usuarios;
	private EntityManager entityManager;
	
	
	/*private static class RepositorioUsuariosHolder {
        static final RepositorioUsuarios singleInstanceRepositorioUsuarios = new RepositorioUsuarios();
    }
	
	public static RepositorioUsuarios getInstance() {
        return RepositorioUsuariosHolder.singleInstanceRepositorioUsuarios;
    }
	
	public RepositorioUsuarios() {
		
		// Inicializacion USUARIOS ADMINISTRADORES DE SISTEMA (Por defecto se crea con estos 3 admins)
				usuarios = new ArrayList<CuentaUsuario>();
				
				usuarios.add(administrador1);
				usuarios.add(administrador2);
				usuarios.add(administrador3);
	}*/
	public RepositorioUsuarios(EntityManager em){
		entityManager = em;
	}

	public void agregarUsuarioAdministrador(String userName, String password){
		Rol rolAdministrador = this.buscarRol("ROL_ADMINISTRADOR_SISTEMA");
		CuentaUsuario nuevoUsuario = new CuentaUsuario(userName, password, rolAdministrador);
		entityManager.persist(nuevoUsuario.getPerfil());
		entityManager.persist(nuevoUsuario);
	}
	
	public void agregarUsuarioEstandar(String unNombreUsuario, Organizacion unaOrganizacion, ArrayList<String> nombresRoles, String unaPassword) {
		ArrayList<Rol> unosRoles = new ArrayList<>();
		nombresRoles.forEach(nombreRol -> unosRoles.add(this.buscarRol(nombreRol)));
		CuentaUsuario nuevoUsuario = new CuentaUsuario(unNombreUsuario, unaOrganizacion, unosRoles, unaPassword);
		entityManager.persist(nuevoUsuario.getPerfil());
		entityManager.persist(nuevoUsuario);
	}

	public void agregarUsuarioEstandar(String unNombreUsuario, Organizacion unaOrganizacion, ArrayList<String> nombresRoles) {
		ArrayList<Rol> unosRoles = new ArrayList<>();
		nombresRoles.forEach(nombreRol -> unosRoles.add(this.buscarRol(nombreRol)));
		CuentaUsuario nuevoUsuario = new CuentaUsuario(unNombreUsuario, unaOrganizacion, unosRoles);
		entityManager.persist(nuevoUsuario.getPerfil());
		entityManager.persist(nuevoUsuario);
	}

	public boolean existeElUsuario(String unNombreUsuario) {
		return entityManager.contains(this.buscarUsuario(unNombreUsuario));
	}
	
	public void eliminarUsuarioEstandar(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException{
		if(this.existeElUsuario(unNombreUsuario)){
			CuentaUsuario usuarioABorrar = this.buscarUsuario(unNombreUsuario);
			if(usuarioABorrar.verificarContrasenia(unaContrasenia)) {
				entityManager.remove(usuarioABorrar);
			}
			else {
				throw new CredencialesNoValidasException();
			}
		}
	}
	
	public CuentaUsuario buscarUsuario(String unNombreUsuario) {
		return entityManager.find(CuentaUsuario.class, unNombreUsuario);
	}
	
	public Rol buscarRol(String nombreRol) {
		return entityManager.find(Rol.class, nombreRol);
	}

}