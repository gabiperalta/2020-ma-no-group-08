package dominio.notificador_suscriptores;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;
import dominio.notificador_suscriptores.bandeja_de_mensajes.RepoBandejaDeMensajes;

public class NotificadorSuscriptores {
	private static ArrayList<Suscripciones> suscripciones;

	private static class NotificadorSuscriptoresHolder {
		static final NotificadorSuscriptores singleInstanceNotificadorSuscriptores = new NotificadorSuscriptores();
	}

	public static NotificadorSuscriptores getInstance() {
		return NotificadorSuscriptoresHolder.singleInstanceNotificadorSuscriptores;
	}

	public NotificadorSuscriptores() {
		suscripciones = new ArrayList<>();
	}

	public void suscribir(CuentaUsuario unaCuentaUsuario, Licitacion licitacion){
		Suscripciones suscripcionesDeLicitacion;
		try{
			suscripcionesDeLicitacion = buscarSuscripciones(licitacion);
			suscripcionesDeLicitacion.agregarSuscriptor(unaCuentaUsuario);
		}catch (NoSuchElementException e){
			suscripcionesDeLicitacion = new Suscripciones(licitacion);
			suscripciones.add(suscripcionesDeLicitacion);
			suscripcionesDeLicitacion.agregarSuscriptor(unaCuentaUsuario);
		}
	}

	/*
	public void desuscribir(CuentaUsuario unaCuentaUsuario, OperacionEgreso operacionEgreso) {
		SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(operacionEgreso);
		suscripcion.desuscribir(unaCuentaUsuario);
	}
	*/

	public void notificar(String mensajeTexto, Licitacion licitacion) {
		Suscripciones suscripcionesDeLicitacion = buscarSuscripciones(licitacion);
		suscripcionesDeLicitacion.getUsuarios().forEach( usuario -> RepoBandejaDeMensajes.getInstance().buscarBandejaDeMensajes(usuario).nuevoMensaje(new Mensaje(mensajeTexto,false)));
	}

	//public void altaSuscripcionOperaciones(OperacionEgreso operacion) {
	//	SuscripcionesAOperacion suscripcionesAOperacion = new SuscripcionesAOperacion(operacion);
	//	suscripciones.add(suscripcionesAOperacion); // TODO, Deberiamos cambiar el constructor de las operaciones de egreso, para que
	//}												// al ser creadas, se den de alta directamente en el NotificadorSuscriptores

	private Suscripciones buscarSuscripciones(Licitacion licitacion) {
		return suscripciones.stream().filter(suscripcion -> suscripcion.getLicitacion().equals(licitacion)).findFirst().get();
	}

}
