package seguridad.usuario;

import java.util.ArrayList;

import seguridad.HashPassword;

public abstract class Usuario {
	
	protected String userName;
	protected String passwordHash;
	protected String passwordPlana; // TODO. este atributo sera usado unicamente para testeo, posteriormente sera eliminado para quedar solo el hash
	protected ArrayList<String> contraseniasPrevias;
	protected Integer intentosPendientes;

	public Usuario(String unUserName, String unaPassword) {
		userName = unUserName;
		passwordHash = HashPassword.calcular(unaPassword);
		passwordPlana = unaPassword;
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
	}
	
	protected String calcularHash(String Password) {
		return Password; // TODO ahora veo como es que se calcula el hash de una password
		// TODO posiblemente delegue esta funicon a una clase maestra de logueos
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<String> getContraseniasPrevias() {
		return contraseniasPrevias;
	}

	public void cambiarContrasenia(String nuevaContrasenia) {
		passwordHash = HashPassword.calcular(nuevaContrasenia);
		passwordPlana = nuevaContrasenia;
		contraseniasPrevias.add(passwordPlana);
	}

	public abstract boolean esAdministrador() ;
	
}
