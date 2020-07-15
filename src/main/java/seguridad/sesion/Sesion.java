package seguridad.sesion;

import java.util.UUID;

import dominio.cuentasUsuarios.CuentaUsuario;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abOperaciones.ServicioABOperaciones;
import servicio.ab_licitaciones.ServicioABLicitaciones;
import servicio.ab_organizaciones.ServicioABOrganizaciones;
import servicio.abm_entidades.ServicioABMEntidadesJuridicas;
import servicio.abm_entidades.ServicioABMEntidadesBase;
import servicio.abm_usuarios.ServicioABMUsuarios;

public class Sesion {
	
	UUID identificadorSesion;
	CuentaUsuario cuentaUsuario;
	
	public Sesion(CuentaUsuario unaCuentaUsuario) {
		cuentaUsuario = unaCuentaUsuario;
		identificadorSesion = UUID.randomUUID(); // Identificador unico para la sesion
	}
	
	public boolean esLaSesion(UUID unIdentificadorSesion) {
		return identificadorSesion.equals(unIdentificadorSesion);
	}
	
	public UUID getIdentificadorSesion() {
		return identificadorSesion;
	}
	
	public String getNombre() {
		return cuentaUsuario.getUserName();
	}
	
	public CuentaUsuario getCuentaUsuario() {
		return cuentaUsuario;
	}
	
	
	public ServicioABOrganizaciones abOrganizaciones() throws PermisoDenegadoException {
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_AB_ORGANIZACIONES")) {
			return new ServicioABOrganizaciones();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");
	}
	
	public ServicioABMUsuarios abmUsuarios() throws PermisoDenegadoException {
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_ABM_USUARIOS")) {
			return new ServicioABMUsuarios();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");
	}

	public ServicioABMEntidadesJuridicas abmEntidadesJuridicas() throws PermisoDenegadoException{
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_ABM_ENTIDADES_JURIDICAS")) {
			return new ServicioABMEntidadesJuridicas();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");		
	}

	public ServicioABMEntidadesBase abmEntidadesBase() throws PermisoDenegadoException{
	if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_ABM_ENTIDADES_BASE")) {
			return new ServicioABMEntidadesBase();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");	
	}
	
	public ServicioABOperaciones abOperacion() throws PermisoDenegadoException{
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_AB_OPERACIONES")) {
			return new ServicioABOperaciones();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");
	}
	
	public ServicioABLicitaciones abLicitaciones() throws PermisoDenegadoException{
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_AB_LICITACIONES")) {
			return new ServicioABLicitaciones();
		}
		throw new PermisoDenegadoException("No tienes los permisos necesarios para realizar esta operacion");
	}
	
	
}
