package seguridad.sesion;

import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_usuarios.ServicioABMUsuarios;

public interface Sesion {
	public String getNombre();
	// OPERACIONES USUARIOS ADMINISTRADORES
	public ServicioABMUsuarios abmUsuarios() throws PermisoDenegadoException;
	public void abOrganizaciones() throws PermisoDenegadoException;
	// OPERACIONES USUARIOS ESTANDAR
	public void abmEntidadesJuridicas() throws PermisoDenegadoException;
	public void abmEntidadesBase() throws PermisoDenegadoException;
	public void abOperacion() throws PermisoDenegadoException;
}
