package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.notificador_suscriptores.bandeja_de_mensajes.RepoBandejaDeMensajes;

public class NotificadorSuscriptores {

	private static class NotificadorSuscriptoresHolder {
		static final NotificadorSuscriptores singleInstanceNotificadorSuscriptores = new NotificadorSuscriptores();
	}

	public static NotificadorSuscriptores getInstance() {
		return NotificadorSuscriptoresHolder.singleInstanceNotificadorSuscriptores;
	}

	public NotificadorSuscriptores() {

	}

	public void notificar(String mensajeTexto, ArrayList<CuentaUsuario> suscriptores) {
		suscriptores.forEach( usuario -> RepoBandejaDeMensajes.getInstance().buscarBandejaDeMensajes(usuario).nuevoMensaje(new Mensaje(mensajeTexto,false)));
	}
}
