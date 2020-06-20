package dominio.notificador_suscriptores.bandeja_de_mensajes;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.presupuestos.Mensaje;

public class BandejaDeMensajes {
	ArrayList<Mensaje> mensajes;
	CuentaUsuario usuario;
	
	public BandejaDeMensajes() {
		mensajes = new ArrayList<Mensaje>();
	}
	
	public void nuevoMensaje(Mensaje nuevoMensaje){
		mensajes.add(nuevoMensaje);
	}
	
	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public Mensaje obtenerMensajePorIndice(int i) {
		return mensajes.get(i);
	}
	
	public void borrarMensajePorIndice(int i) {
		mensajes.remove(i);
	}
}
