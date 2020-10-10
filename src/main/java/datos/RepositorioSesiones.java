package datos;

import java.util.ArrayList;
import java.util.UUID;

import dominio.cuentasUsuarios.CuentaUsuario;
import seguridad.sesion.Sesion;

public class RepositorioSesiones {
	
	ArrayList<Sesion> sesiones;
	
	private static class RepositorioSesionesHolder {		
        static final RepositorioSesiones singleInstanceRepositorioSesiones = new RepositorioSesiones();
    }
	
	public static RepositorioSesiones getInstance() {
        return RepositorioSesionesHolder.singleInstanceRepositorioSesiones;
    }
	
	public RepositorioSesiones() {
		this.sesiones = new ArrayList<Sesion>();
	}
	
	public Sesion buscarSesion(UUID identificadorSesion) {
		Sesion unaSesion = sesiones.stream().filter(sesion -> sesion.esLaSesion(identificadorSesion)).findFirst().get();
		return unaSesion;
	}
	
	public UUID logInSesion(CuentaUsuario unaCuentaUsuario) {
		Sesion sesionNueva = new Sesion(unaCuentaUsuario);
		
		sesiones.add(sesionNueva);
		
		return sesionNueva.getIdentificadorSesion();
	}
	
	public void logOutSesion(UUID identificadorSesion) {
		sesiones.removeIf(sesion -> sesion.esLaSesion(identificadorSesion));
	}
	
	
	
	
}
