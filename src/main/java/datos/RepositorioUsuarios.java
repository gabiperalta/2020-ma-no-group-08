package datos;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import dominio.cuentasUsuarios.perfil.*;

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

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<PerfilEstandar> consulta = cb.createQuery(PerfilEstandar.class);
		Root<PerfilEstandar> perfiles = consulta.from(PerfilEstandar.class);
		Predicate condicion = cb.equal(perfiles.get("nombre"), unNombreUsuario);

		CriteriaQuery<PerfilEstandar> where = consulta.select(perfiles).where(condicion);

		List<PerfilEstandar> listaPerfiles = this.entityManager.createQuery(where).getResultList();

		Object perfilUsuario;

		if(listaPerfiles.size() != 0) {
			perfilUsuario = listaPerfiles.get(0);

		}
		else{

			CriteriaBuilder cb2 = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<PerfilAdministrador> consulta2 = cb2.createQuery(PerfilAdministrador.class);
			Root<PerfilAdministrador> perfilesA = consulta.from(PerfilAdministrador.class);
			Predicate condicion2 = cb.equal(perfiles.get("nombre"), unNombreUsuario);

			CriteriaQuery<PerfilAdministrador> where2 = consulta2.select(perfilesA).where(condicion2);

			List<PerfilAdministrador> listaPerfiles2 = this.entityManager.createQuery(where2).getResultList();

			if(listaPerfiles2.size() != 0) {
				perfilUsuario = listaPerfiles2.get(0);
			}
			else {
				return null;
			}

		}

		CriteriaBuilder cb3 = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<CuentaUsuario> consulta3 = cb3.createQuery(CuentaUsuario.class);
		Root<CuentaUsuario> cuentas = consulta3.from(CuentaUsuario.class);
		Predicate condicion3 = cb.equal(cuentas.get("perfil"), perfilUsuario);
		CriteriaQuery<CuentaUsuario> where3 = consulta3.select(cuentas).where(condicion3);

		List<CuentaUsuario> listaCuentas = this.entityManager.createQuery(where3).getResultList();

		if(listaCuentas.size() > 0)
			return listaCuentas.get(0);
		else
			return null;

	}
	
	public Rol buscarRol(String nombreRol) {


		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Rol> consulta = cb.createQuery(Rol.class);
		Root<Rol> roles = consulta.from(Rol.class);
		Predicate condicion = cb.equal(roles.get("nombre"), nombreRol);
		CriteriaQuery<Rol> where = consulta.select(roles).where(condicion);

		List<Rol> listaRoles = this.entityManager.createQuery(where).getResultList();

		if(listaRoles.size() > 0)
			return listaRoles.get(0);
		else
			return null;

	}

}