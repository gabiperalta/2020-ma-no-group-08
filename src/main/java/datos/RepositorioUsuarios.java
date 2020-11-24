package datos;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

import dominio.cuentasUsuarios.perfil.*;

import dominio.cuentasUsuarios.Roles.Rol;
import dominio.entidades.Organizacion;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import javax.persistence.EntityManager;


public class RepositorioUsuarios {

	private EntityManager entityManager;

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
		if(!this.existeElUsuario(unNombreUsuario)){
			ArrayList<Rol> unosRoles = new ArrayList<>();
			nombresRoles.forEach(nombreRol -> unosRoles.add(this.buscarRol(nombreRol)));
			CuentaUsuario nuevoUsuario = new CuentaUsuario(unNombreUsuario, unaOrganizacion, unosRoles, unaPassword);
			entityManager.persist(nuevoUsuario.getPerfil());
			entityManager.persist(nuevoUsuario);
		}
	}

	public void agregarUsuarioEstandar(String unNombreUsuario, Organizacion unaOrganizacion, ArrayList<String> nombresRoles) {
		ArrayList<Rol> unosRoles = new ArrayList<>();
		nombresRoles.forEach(nombreRol -> unosRoles.add(this.buscarRol(nombreRol)));
		CuentaUsuario nuevoUsuario = new CuentaUsuario(unNombreUsuario, unaOrganizacion, unosRoles);
		entityManager.persist(nuevoUsuario.getPerfil());
		entityManager.persist(nuevoUsuario);
	}

	public boolean existeElUsuario(String unNombreUsuario) {
		CuentaUsuario cuentaUsuario = this.buscarUsuario(unNombreUsuario);
		return entityManager.contains(cuentaUsuario);
	}
	
	public void eliminarUsuarioEstandar(String unNombreUsuario, String unaContrasenia) throws CredencialesNoValidasException{
		if(this.existeElUsuario(unNombreUsuario)){
			CuentaUsuario usuarioABorrar = this.buscarUsuario(unNombreUsuario);
			if(usuarioABorrar.verificarContrasenia(unaContrasenia)) {
				Perfil perfilABorrar = usuarioABorrar.getPerfil();
				entityManager.remove(usuarioABorrar);
				entityManager.remove(perfilABorrar);
			}
			else {
				throw new CredencialesNoValidasException();
			}
		}
	}

	public CuentaUsuario buscarUsuario(String unNombreUsuario){
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<CuentaUsuario> consulta = cb.createQuery(CuentaUsuario.class);
		Root<CuentaUsuario> cuentasUsuario = consulta.from(CuentaUsuario.class);
		Join<Object,Object> perfil = cuentasUsuario.join("perfil",JoinType.INNER);
		Predicate condicion = cb.equal(perfil.get("nombre"), unNombreUsuario);

		consulta.select(cuentasUsuario).where(condicion);

		Query query = entityManager.createQuery(consulta);
		List<CuentaUsuario> listaCuentasUsuario = query.getResultList();

		if(listaCuentasUsuario.size() > 0)
			return listaCuentasUsuario.get(0);
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