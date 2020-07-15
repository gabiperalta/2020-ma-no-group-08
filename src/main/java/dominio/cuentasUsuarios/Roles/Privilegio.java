package dominio.cuentasUsuarios.Roles;

public class Privilegio {
	private String nombre;
	
	public Privilegio(String unNombre) {
		nombre = unNombre;
	}
	
	public boolean esElPrivilegio(String unNombrePrivilegio) {
		return nombre.equals(unNombrePrivilegio);
	}
}
