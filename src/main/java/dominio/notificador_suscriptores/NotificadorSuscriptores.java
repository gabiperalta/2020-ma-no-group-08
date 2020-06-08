package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.OperacionEgreso;
import dominio.presupuestos.ResultadoLicitacion;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

public class NotificadorSuscriptores {
	ArrayList<SuscripcionesAOperacion> suscripciones;
	
	private static class NotificadorSuscriptoresHolder {		
        static final NotificadorSuscriptores singleInstanceNotificadorSuscriptores = new NotificadorSuscriptores();
    }
	
	public static NotificadorSuscriptores getInstance() {
        return NotificadorSuscriptoresHolder.singleInstanceNotificadorSuscriptores;
	}
	
	public NotificadorSuscriptores() {
		suscripciones = new ArrayList<SuscripcionesAOperacion>();
	}
	
	public void suscribir(CuentaUsuario unaCuentaUsuario, OperacionEgreso operacionEgreso){
		SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(operacionEgreso);
		suscripcion.suscribir(unaCuentaUsuario);
	}
	
	public void desuscribir(CuentaUsuario unaCuentaUsuario, OperacionEgreso operacionEgreso) {
		SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(operacionEgreso);
		suscripcion.desuscribir(unaCuentaUsuario);
	}
	
	public void notificar(ResultadoLicitacion resultadoLicitacion) {
		SuscripcionesAOperacion suscripcion = buscarSuscripcionesOperacion(resultadoLicitacion.getOperacion());
		suscripcion.getUsuarios().forEach( cuentaUsuario -> cuentaUsuario.getBandejaDeMensajes().nuevoMensaje(resultadoLicitacion));
	}
	
	public void altaSuscripcionOperaciones(OperacionEgreso operacion) {
		SuscripcionesAOperacion suscripcionesAOperacion = new SuscripcionesAOperacion(operacion);
		suscripciones.add(suscripcionesAOperacion); // TODO, Deberiamos cambiar el constructor de las operaciones de egreso, para que
	}												// al ser creadas, se den de alta directamente en el NotificadorSuscriptores
	
	private SuscripcionesAOperacion buscarSuscripcionesOperacion( OperacionEgreso operacionEgreso) {
		return suscripciones.stream().filter(suscripcion -> suscripcion.getOperacionEgreso().equals(operacionEgreso)).findFirst().get();
	}
	
}
