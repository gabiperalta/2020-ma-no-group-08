package seguridad.usuario;

import java.util.ArrayList;

public abstract class Usuario {
	
	protected String userName;
	protected String passwordHash;
	protected ArrayList<String> contraseniasPrevias;
	protected Integer intentosPendientes;

	public Usuario(String unUserName, String unaPassword) {
		userName = unUserName;
		passwordHash = this.calcularHash(unaPassword);
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

	public abstract boolean esAdministrador() ;
	
}
