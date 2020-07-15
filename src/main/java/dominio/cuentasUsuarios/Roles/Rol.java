package dominio.cuentasUsuarios.Roles;

import java.util.ArrayList;

public class Rol {
	
	private String nombre;
	private ArrayList<Privilegio> privilegios;
	
	public Rol(String unNombre, ArrayList<Privilegio> unosPrivilegios) {
		nombre = unNombre;
		privilegios = unosPrivilegios;
	}
	
	public boolean esElRol(String nombreRol) {
		return nombre.equals(nombreRol);
	}
	
	public boolean tieneElPrivilegio(String nombrePrivilegio) {
		return privilegios.stream().anyMatch(privilegio -> privilegio.esElPrivilegio(nombrePrivilegio));
	}
	
}
