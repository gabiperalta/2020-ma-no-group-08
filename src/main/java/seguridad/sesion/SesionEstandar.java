package seguridad.sesion;

import dominio.entidades.Organizacion;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_usuarios.ServicioABMUsuarios;
import servicio.abm_entidades.ServicioABMEntidadesJuridicas;


public class SesionEstandar implements Sesion {
	private String nombre;
	private Organizacion organizacion;
	
	public SesionEstandar(String unNombre, Organizacion unaOrganizacion){
		nombre = unNombre;
		organizacion = unaOrganizacion;
	}
	
	@Override
	public void abmEntidadesJuridicas() throws PermisoDenegadoException{


		// TODO
	}
	
	@Override
	public void abmEntidadesBase() {
		// TODO
	}
	
	@Override
	public void abOperacion(){
		// TODO
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
	
	
	

	@Override
	public ServicioABMUsuarios abmUsuarios() throws PermisoDenegadoException{
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");
	}

	@Override
	public void abOrganizaciones() throws PermisoDenegadoException{
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");		
	}
}
