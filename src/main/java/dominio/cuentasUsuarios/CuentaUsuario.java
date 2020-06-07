package dominio.cuentasUsuarios;

import java.util.ArrayList;

import dominio.cuentasUsuarios.perfil.Perfil;
import dominio.entidades.Organizacion;
import seguridad.HashPassword;

public class CuentaUsuario {

	protected Perfil perfil;
	protected String passwordHash;
	protected String passwordPlana; // TODO. este atributo sera usado unicamente para testeo, posteriormente sera eliminado para quedar solo el hash
	protected ArrayList<String> contraseniasPrevias;
	protected Integer intentosPendientes;

	public CuentaUsuario(Perfil unPerfil, String unaPassword) {
		perfil = unPerfil;
		passwordHash = HashPassword.calcular(unaPassword);
		passwordPlana = unaPassword;
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
	}
	
	public boolean verificarContrasenia(String contrasenia) {
		if(passwordHash.equals(contrasenia)) {
			intentosPendientes = 3; // Reinicio el contador de intentos pendientes 
			return true;
		}
		else {
			intentosPendientes --;
			return false;
		}
	}
	
	public boolean estaBloqueada() {
		return intentosPendientes > 0;
	}
	
	public void setUserName(String unNombreUsuario) {
		perfil.setNombre(unNombreUsuario);
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
	
	public Organizacion getOrganizacion() {
		return perfil.getOrganizacion();
	}

	public void actualizarContrasenia(String contraseniaPlanaNueva, String contraseniaHasheadaNueva) {
		passwordPlana = contraseniaPlanaNueva;
		passwordHash = contraseniaHasheadaNueva;
		intentosPendientes = 3;
		contraseniasPrevias.add(contraseniaPlanaNueva);
	}
}
