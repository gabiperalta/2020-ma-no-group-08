package dominio.cuentasUsuarios;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import dominio.cuentasUsuarios.Roles.Rol;
import dominio.cuentasUsuarios.perfil.Perfil;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import dominio.cuentasUsuarios.perfil.PerfilEstandar;
import dominio.entidades.Organizacion;
import org.hibernate.annotations.Cascade;
import seguridad.HashPassword;
import seguridad.ValidadorContrasenia;
import datos.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

import javax.persistence.*;

@Entity
@Table(name = "cuentas_usuarios")
public class CuentaUsuario implements Serializable {
	@Id
	@OneToOne
	private Perfil perfil;
	private String passwordHash;
	private String passwordPlana;
	@Transient
	private List<String> contraseniasPrevias;
	private Integer intentosPendientes;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Rol> roles;

	public CuentaUsuario() { }

	// CONSTRUCTOR USUARIOS ADMINISTRADOR
	public CuentaUsuario(String unNombreUsuario, String unaPassword, Rol rolAdmin) {
		perfil = new PerfilAdministrador(unNombreUsuario);
		passwordHash = HashPassword.calcular(unaPassword);
		passwordPlana = unaPassword;
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		roles = new ArrayList<Rol>();
		roles.add(rolAdmin);
	}

	// CONSTRUCTOR PARA USUARIOS ESTANDAR CON PASSWORD SETEADA
	public CuentaUsuario(String unNombreUsuario, Organizacion unaOrganizacion, ArrayList<Rol> unosRoles, String unaPassword){
		perfil = new PerfilEstandar(unNombreUsuario, unaOrganizacion);
		passwordPlana = unaPassword;
		passwordHash = HashPassword.calcular(passwordPlana);
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		roles = unosRoles;
	}

	// CONSTRUCTOR USUARIOS ESTANDAR
	public CuentaUsuario(String unNombreUsuario, Organizacion unaOrganizacion, ArrayList<Rol> unosRoles) {
		perfil = new PerfilEstandar(unNombreUsuario, unaOrganizacion);
		
		String unaPassword = this.generarContrasenia();
		passwordPlana = unaPassword;
		passwordHash = HashPassword.calcular(passwordPlana);
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		roles = unosRoles;
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
	
	public void cambiarNombre(String unNombreUsuario, RepositorioUsuarios repositorioUsuarios) throws UsuarioYaExistenteException {
		if(!repositorioUsuarios.existeElUsuario(unNombreUsuario)) {
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
		return new ArrayList<>(contraseniasPrevias);
	}

	public boolean esAdministrador() {
		return perfil.esUsuarioAdministrador();
	}

	public boolean getEsAdministrador() {
		return perfil.esUsuarioAdministrador();
	}
	
	public Organizacion getOrganizacion() {
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
		return tieneElPrivilegio("PRIVILEGIO_RECATEGORIZADOR");
	}

	public boolean puedeVincular() {
		return tieneElPrivilegio("PRIVILEGIO_VINCULADOR");
	}

	public boolean puedeLicitar() {
		return tieneElPrivilegio("PRIVILEGIO_AB_LICITACIONES");
	}

	public boolean puedeSerRevisor() {
		return tieneElPrivilegio("PRIVILEGIO_REVISOR");
	}

	public boolean puedeAdministarEntidadesJuridicas() {
		return tieneElPrivilegio("PRIVILEGIO_ABM_ENTIDADES_JURIDICAS");
	}

	public boolean puedeAdministarEntidadesBase() {
		return tieneElPrivilegio("PRIVILEGIO_ABM_ENTIDADES_BASE");
	}

	public boolean puedeAdministrarOperaciones() {
		return tieneElPrivilegio("PRIVILEGIO_AB_OPERACIONES");
	}

	public boolean puedeAdministrarOrganizaciones() {
		return tieneElPrivilegio("PRIVILEGIO_AB_ORGANIZACIONES");
	}

	public boolean puedeAdministrarUsuarios() {
		return tieneElPrivilegio("PRIVILEGIO_ABM_USUARIOS");
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

		// Esta contrase�a autogenerada deberia ser enviada al usuario final de forma segura
		
		return generatedString;
	}
	
	private void addRol(Rol unRol) {
		this.roles.add(unRol);
	}

	public ArrayList<Rol> getRoles(){
		return new ArrayList<>(this.roles);
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setContraseniasPrevias(List<String> contraseniasPrevias) {
		this.contraseniasPrevias = contraseniasPrevias;
	}

	public String getPasswordPlana() {
		return passwordPlana;
	}

	public void setPasswordPlana(String passwordPlana) {
		this.passwordPlana = passwordPlana;
	}

	public Integer getIntentosPendientes() {
		return intentosPendientes;
	}

	public void setIntentosPendientes(Integer intentosPendientes) {
		this.intentosPendientes = intentosPendientes;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}