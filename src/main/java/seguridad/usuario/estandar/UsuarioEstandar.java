package seguridad.usuario.estandar;


import seguridad.usuario.Usuario;


public class UsuarioEstandar extends Usuario {
	
	public UsuarioEstandar(String unNombreOrganizacion, String unaContraseña) {
		super(unNombreOrganizacion, unaContraseña);
	}

	@Override
	public boolean esAdministrador() {
		return false;
	}
	
	public void altaOperacion() {
		// TODO
	}
	
	public void bajaOperacion() {
		// TODO
	}

	public void modificacionOperacion() {
		// TODO
	}
	
	public void altaEntidad() {
		// TODO
	}
	
	public void bajaEntidad() {
		// TODO
	}
	
	public void modificacionEntidad() {
		// TODO
	}
}
