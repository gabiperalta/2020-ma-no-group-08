package seguridad.sesion;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abOperaciones.ServicioABOperaciones;
import servicio.abm_entidades.ServicioABMEntidadesBase;
import servicio.abm_usuarios.ServicioABMUsuarios;
import servicio.abm_entidades.ServicioABMEntidadesJuridicas;


public class SesionEstandar implements Sesion {
	private String nombre;
	private EntidadOperacion organizacion;
	
	public SesionEstandar(String unNombre, EntidadOperacion unaOrganizacion){
		nombre = unNombre;
		organizacion = unaOrganizacion;
	}
	
	@Override
	public ServicioABMEntidadesJuridicas abmEntidadesJuridicas(){
		return new ServicioABMEntidadesJuridicas();
	}
	
	@Override
	public ServicioABMEntidadesBase abmEntidadesBase() {
		return new ServicioABMEntidadesBase();
	}
	
	@Override
	public ServicioABOperaciones abOperacion(){
		return new ServicioABOperaciones();
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

	public EntidadOperacion getOrganizacion() {
		return organizacion;
	}
	
	
}
