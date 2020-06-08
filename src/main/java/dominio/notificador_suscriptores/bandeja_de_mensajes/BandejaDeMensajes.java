package dominio.notificador_suscriptores.bandeja_de_mensajes;

import java.util.ArrayList;

import dominio.presupuestos.ResultadoLicitacion;

public class BandejaDeMensajes {
	ArrayList<ResultadoLicitacion> mensajes;
	
	public BandejaDeMensajes() {
		mensajes = new ArrayList<ResultadoLicitacion>();
	}
	
	public void nuevoMensaje(ResultadoLicitacion nuevoResultadoLicitacion){
		mensajes.add(nuevoResultadoLicitacion);
	}
	
	public ArrayList<ResultadoLicitacion> getMensajes() {
		return mensajes;
	}
	
	public ResultadoLicitacion obtenerMensajePorIndice(int i) {
		return mensajes.get(i);
	}
	
	public void borrarMensajePorIndice(int i) {
		mensajes.remove(i);
	}
}
