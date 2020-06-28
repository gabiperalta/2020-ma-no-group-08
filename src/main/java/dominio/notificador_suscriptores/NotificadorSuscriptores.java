package dominio.notificador_suscriptores;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Mensaje;

public class NotificadorSuscriptores {
	Suscripciones suscripciones;
	
	private static class NotificadorSuscriptoresHolder {		
        static final NotificadorSuscriptores singleInstanceNotificadorSuscriptores = new NotificadorSuscriptores();
    }
	
	public static NotificadorSuscriptores getInstance() {
        return NotificadorSuscriptoresHolder.singleInstanceNotificadorSuscriptores;
	}
	
	public NotificadorSuscriptores() {

	}
	
	public void suscribir(CuentaUsuario unaCuentaUsuario, Licitacion licitacion){
		try{
			suscripciones.suscribirse(unaCuentaUsuario);
		}catch (NoSuchElementException e){
			suscripciones = new Suscripciones(licitacion);
			suscripciones.suscribirse(unaCuentaUsuario);
		}
	}

	/*
	public void desuscribir(CuentaUsuario unaCuentaUsuario, OperacionEgreso operacionEgreso) {
		SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(operacionEgreso);
		suscripcion.desuscribir(unaCuentaUsuario);
	}
	*/
	
	public void notificar(Mensaje mensaje) {
		//SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(Mensaje.getOperacion());
		//suscripcion.getUsuarios().forEach( cuentaUsuario -> cuentaUsuario.getBandejaDeMensajes().nuevoMensaje(mensaje));
		//suscripcion.getUsuarios().forEach(usuario->usuario.leerMensajes());
	}
	
	//public void altaSuscripcionOperaciones(OperacionEgreso operacion) {
	//	SuscripcionesAOperacion suscripcionesAOperacion = new SuscripcionesAOperacion(operacion);
	//	suscripciones.add(suscripcionesAOperacion); // TODO, Deberiamos cambiar el constructor de las operaciones de egreso, para que
	//}												// al ser creadas, se den de alta directamente en el NotificadorSuscriptores
	
	//private SuscripcionesAOperacion buscarSuscripcionesOperacion( OperacionEgreso operacionEgreso) {
	// return suscripciones.stream().filter(suscripcion -> suscripcion.getOperacionEgreso().equals(operacionEgreso)).findFirst().get();
	//}
	
}
