package seguridad.sesion;

import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_entidades.ServicioABMEntidadesJuridicas;
import servicio.abm_entidades.ServicioABMEntidadesBase;
import servicio.abm_usuarios.ServicioABMUsuarios;

public interface Sesion {
	public String getNombre();
	// OPERACIONES USUARIOS ADMINISTRADORES
	public ServicioABMUsuarios abmUsuarios() throws PermisoDenegadoException;
	public void abOrganizaciones() throws PermisoDenegadoException;
	// OPERACIONES USUARIOS ESTANDAR
	public ServicioABMEntidadesJuridicas abmEntidadesJuridicas() throws PermisoDenegadoException;
	public ServicioABMEntidadesBase abmEntidadesBase() throws PermisoDenegadoException;
	public void abOperacion() throws PermisoDenegadoException;
}
