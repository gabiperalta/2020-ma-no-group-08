package dominio.cuentasUsuarios;

import java.security.SecureRandom;
import java.util.ArrayList;

import dominio.cuentasUsuarios.Roles.Rol;
import dominio.cuentasUsuarios.perfil.Perfil;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import dominio.cuentasUsuarios.perfil.PerfilEstandar;
import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import seguridad.HashPassword;
import seguridad.ValidadorContrasenia;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;


public class CuentaUsuario {

	private Perfil perfil;
	private String passwordHash;
	private String passwordPlana; // TODO. este atributo sera usado unicamente para testeo, posteriormente sera eliminado para quedar solo el hash
	private ArrayList<String> contraseniasPrevias;
	private Integer intentosPendientes;
	private ArrayList<Rol> roles;

	public CuentaUsuario(String unNombreUsuario, String unaPassword) { // CONSTRUCTOR USUARIOS ADMINISTRADOR
		perfil = new PerfilAdministrador(unNombreUsuario);
		passwordHash = HashPassword.calcular(unaPassword);
		passwordPlana = unaPassword;
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		roles = new ArrayList<Rol>();
		roles.add(RepositorioUsuarios.getInstance().buscarRol("ROL_ADMINISTRADOR_SISTEMA"));
	}
	
	public CuentaUsuario(String unNombreUsuario, EntidadOperacion unaOrganizacion, ArrayList<String> nombresRoles) { // CONSTRUCTOR USUARIOS ESTANDAR
		perfil = new PerfilEstandar(unNombreUsuario, unaOrganizacion);
		
		String unaPassword = this.generarContrasenia();
		passwordPlana = unaPassword;
		
		passwordHash = HashPassword.calcular(passwordPlana);
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		
		nombresRoles.forEach(nombreRol -> this.addRol(RepositorioUsuarios.getInstance().buscarRol(nombreRol)));
		
		RepositorioUsuarios.getInstance().agregarUsuarioEstandar(this);
	}
	
	public boolean verificarContrasenia(String contrasenia) {
		
		if(passwordHash.equals(HashPassword.calcular(contrasenia))) {
			intentosPendientes = 3;
			return true;
		}
		else {
			intentosPendientes --;
			return false;
		}
	}
	
	public boolean estaBloqueada() {
		return intentosPendientes == 0; //return intentosPendientes > 0;
	}
	
	public void cambiarNombre(String unNombreUsuario) throws UsuarioYaExistenteException {
		if(!RepositorioUsuarios.getInstance().existeElUsuario(unNombreUsuario)) {
			perfil.setNombre(unNombreUsuario);
		}
		else {
			throw new UsuarioYaExistenteException("Este nombre de usuario ya esta en uso.");
		}
	}
	
	public String getUserName() {
		return perfil.getNombre();
	}

	public ArrayList<String> getContraseniasPrevias() {
		return contraseniasPrevias;
	}

	public boolean esAdministrador() {
		return perfil.esUsuarioAdministrador();
	}
	
	public EntidadOperacion getOrganizacion() {
		return perfil.getOrganizacion();
	}
	
	public void blanquearContrasenia() throws Exception{
		
		ValidadorContrasenia validador = new ValidadorContrasenia();
		String contrasenia = this.generarContrasenia();

		if(validador.esContraseniaValida(contrasenia, this.getContraseniasPrevias())) {
			this.actualizarContrasenia(contrasenia, HashPassword.calcular(contrasenia));
		} else {
			throw new CredencialesNoValidasException("la contrasenia no es valida");
		}
	}

	public void actualizarContrasenia(String contraseniaPlanaNueva, String contraseniaHasheadaNueva) {
		passwordPlana = contraseniaPlanaNueva;
		passwordHash = contraseniaHasheadaNueva;
		intentosPendientes = 3;
		contraseniasPrevias.add(contraseniaPlanaNueva);
	}

	public boolean puedeRecategorizar() {
		//		TODO: definir roles
		return true;
	}
	
	public ArrayList<Rol> getRoles(){
		return this.roles;
	}
	
	public boolean tieneElPrivilegio(String nombrePrivilegio) {
		return roles.stream().anyMatch(rol -> rol.tieneElPrivilegio(nombrePrivilegio));
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
	
	private void addRol(Rol unRol) {
		this.roles.add(unRol);
	}
	
}