package dominio.cuentasUsuarios;

import java.util.ArrayList;

import dominio.cuentasUsuarios.perfil.Perfil;
import dominio.entidades.Organizacion;
import dominio.notificador_suscriptores.bandeja_de_mensajes.BandejaDeMensajes;
import dominio.presupuestos.Mensaje;
import seguridad.HashPassword;

public class CuentaUsuario {

	private Perfil perfil;
	private String passwordHash;
	private String passwordPlana; // TODO. este atributo sera usado unicamente para testeo, posteriormente sera eliminado para quedar solo el hash
	private ArrayList<String> contraseniasPrevias;
	private Integer intentosPendientes;
	private BandejaDeMensajes bandejaDeMensajes;
	

	public CuentaUsuario(Perfil unPerfil, String unaPassword) {
		perfil = unPerfil;
		passwordHash = HashPassword.calcular(unaPassword);
		passwordPlana = unaPassword;
		contraseniasPrevias = new ArrayList<String>();
		intentosPendientes = 3;
		bandejaDeMensajes = new BandejaDeMensajes();
	}
	
	public boolean verificarContrasenia(String contrasenia) {
		
		if(passwordHash.equals(HashPassword.calcular(contrasenia))) {
			intentosPendientes = 3; // Reinicio el contador de intentos pendientes 
			return true;
		}
		else {
			intentosPendientes --;
			return false;
		}
	}
	
	public boolean estaBloqueada() {
		return intentosPendientes == 0; //return intentosPendientes > 0;
	}
	
	public void setUserName(String unNombreUsuario) {
		perfil.setNombre(unNombreUsuario);
	}
	
	public String getUserName() {
		return perfil.getNombre();
	}

	public ArrayList<String> getContraseniasPrevias() {
		return contraseniasPrevias;
	}

	public boolean esAdministrador() {
		return perfil.esUsuarioAdministrador();
	}
	
	public Organizacion getOrganizacion() {
		return perfil.getOrganizacion();
	}
	
	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}

	public void actualizarContrasenia(String contraseniaPlanaNueva, String contraseniaHasheadaNueva) {
		passwordPlana = contraseniaPlanaNueva;
		passwordHash = contraseniaHasheadaNueva;
		intentosPendientes = 3;
		contraseniasPrevias.add(contraseniaPlanaNueva);
	}
	
	public String getPasswordPlana() {
		return passwordPlana;
	}
	public void leerMensajes() {
		ArrayList<Mensaje> mensajes = this.getBandejaDeMensajes().getMensajes();
		mensajes.forEach(mensaje->mensaje.marcarComoLeido());
	}

	public boolean puedeRecategorizar() {
//		TODO: definir roles
		return true;
	}
}
