package seguridad.sesion;

import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_usuarios.ServicioABMUsuarios;

public class SesionAdministrador implements Sesion {
	private String nombre;
	
	public SesionAdministrador(String unNombre){
		nombre = unNombre;
	}
	
	@Override
	public void abOrganizaciones() {
		// TODO
	}
	
	@Override
	public ServicioABMUsuarios abmUsuarios() {
		return new ServicioABMUsuarios();
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	

	
	@Override
	public void abmEntidadesJuridicas() throws PermisoDenegadoException{
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");		
	}

	@Override
	public void abmEntidadesBase() throws PermisoDenegadoException{
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");		
	}

	@Override
	public void abOperacion() throws PermisoDenegadoException{
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");		
	}
}
