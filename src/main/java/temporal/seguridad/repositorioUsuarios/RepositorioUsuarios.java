package temporal.seguridad.repositorioUsuarios;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.operaciones.EntidadOperacion;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

// SINGLETON
public class RepositorioUsuarios {
	
	private ArrayList<CuentaUsuario> usuarios;
	private ArrayList<Rol> roles;
	
	
	private static class RepositorioUsuariosHolder {		
        static final RepositorioUsuarios singleInstanceRepositorioUsuarios = new RepositorioUsuarios();
    }
	
	public static RepositorioUsuarios getInstance() {
        return RepositorioUsuariosHolder.singleInstanceRepositorioUsuarios;
    }
	
	public RepositorioUsuarios() {		
		
		// Inicializacion Roles->Privilegios
		
		Privilegio privilegioABOrganizacion = new Privilegio("PRIVILEGIO_AB_ORGANIZACIONES");
		Privilegio privilegioABMUsuarios = new Privilegio("PRIVILEGIO_ABM_USUARIOS");
		Privilegio privilegioABMEntidadesJuridicas = new Privilegio("PRIVILEGIO_ABM_ENTIDADES_JURIDICAS");
		Privilegio privilegioABMEntidadesBase = new Privilegio("PRIVILEGIO_ABM_ENTIDADES_BASE");
		Privilegio privilegioABOperacion = new Privilegio("PRIVILEGIO_AB_OPERACIONES");
		Privilegio privilegioABLicitaciones = new Privilegio("PRIVILEGIO_AB_LICITACIONES");
		Privilegio privilegioRevisor = new Privilegio("PRIVILEGIO_REVISOR");
		Privilegio privilegioRecategorizador = new Privilegio("PRIVILEGIO_RECATEGORIZADOR");
		Privilegio privilegioVinculador = new Privilegio("PRIVILEGIO_VINCULADOR");

		ArrayList<Privilegio> privilegiosRolAdministradorSistema = new ArrayList<Privilegio>();
		ArrayList<Privilegio> privilegiosRolAdministradorOrganizacion = new ArrayList<Privilegio>();
		ArrayList<Privilegio> privilegiosRolEstandar = new ArrayList<Privilegio>();
		ArrayList<Privilegio> privilegiosRolRevisor = new ArrayList<Privilegio>();
		
		privilegiosRolAdministradorSistema.add(privilegioABOrganizacion);
		privilegiosRolAdministradorSistema.add(privilegioABMUsuarios);
		privilegiosRolAdministradorOrganizacion.add(privilegioABMEntidadesJuridicas);
		privilegiosRolAdministradorOrganizacion.add(privilegioABMEntidadesBase);
		privilegiosRolEstandar.add(privilegioABOperacion);
		privilegiosRolEstandar.add(privilegioABLicitaciones);
		privilegiosRolEstandar.add(privilegioRecategorizador);
		privilegiosRolEstandar.add(privilegioVinculador);

		privilegiosRolRevisor.add(privilegioRevisor);
		
		Rol rolAdministradorSistema = new Rol("ROL_ADMINISTRADOR_SISTEMA", privilegiosRolAdministradorSistema);
		Rol rolAdministradorOrganizacion = new Rol("ROL_ADMINISTRADOR_ORGANIZACION", privilegiosRolAdministradorOrganizacion);
		Rol rolEstandar = new Rol("ROL_ESTANDAR", privilegiosRolEstandar);
		Rol rolRevisor = new Rol("ROL_REVISOR", privilegiosRolRevisor);
		this.roles = new ArrayList<Rol>();
		
		this.roles.add(rolAdministradorSistema);
		this.roles.add(rolAdministradorOrganizacion);
		this.roles.add(rolEstandar);
		this.roles.add(rolRevisor);
		
		// Inicializacion USUARIOS ADMINISTRADORES DE SISTEMA (Por defecto se crea con estos 3 admins)
		
				CuentaUsuario administrador1 = new CuentaUsuario("admin1", "1234", rolAdministradorSistema);
				CuentaUsuario administrador2 = new CuentaUsuario("admin2", "1234", rolAdministradorSistema);
				CuentaUsuario administrador3 = new CuentaUsuario("admin3", "1234", rolAdministradorSistema);
				usuarios = new ArrayList<CuentaUsuario>();
				
				usuarios.add(administrador1);
				usuarios.add(administrador2);
				usuarios.add(administrador3);
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
		return unUsuario;
	}
	
	public Rol buscarRol(String nombreRol) {
		Rol unRol = roles.stream().filter(rol -> rol.esElRol(nombreRol)).findFirst().get();
		return unRol;
	}

	public void inicializarClientesParaWeb(){
		// Inicializacion USUARIOS CLIENTES PARA PRUEBAS WEB

		ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
		listaDeRolesCliente.add("ROL_ESTANDAR");
		ArrayList<String> listaDeRolesClienteMaestro = new ArrayList<String>();
		listaDeRolesClienteMaestro.add("ROL_ADMINISTRADOR_ORGANIZACION");
		listaDeRolesClienteMaestro.add("ROL_ESTANDAR");
		listaDeRolesClienteMaestro.add("ROL_REVISOR");

		CuentaUsuario usuarioClientePruebasWeb = new CuentaUsuario("UsuarioWeb1", null, listaDeRolesCliente, "1234");
		CuentaUsuario usuarioClienteMaestroPruebasWeb = new CuentaUsuario("UsuarioWeb2", null, listaDeRolesClienteMaestro, "1234");
		usuarios.add(usuarioClientePruebasWeb);
		usuarios.add(usuarioClienteMaestroPruebasWeb);
	}
}