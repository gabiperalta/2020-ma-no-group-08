package dominio.notificador_suscriptores.bandeja_de_mensajes;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.notificador_suscriptores.Mensaje;

public class BandejaDeMensajes {
	ArrayList<Mensaje> mensajes;
	CuentaUsuario usuario;
	
	public BandejaDeMensajes(CuentaUsuario usuario) {
		mensajes = new ArrayList<>();
		this.usuario = usuario;
	}
	
	public void nuevoMensaje(Mensaje nuevoMensaje){
		mensajes.add(nuevoMensaje);
	}
	
	public ArrayList<Mensaje> getMensajes() {
		mensajes.forEach(Mensaje::marcarComoLeido);
		return mensajes;
	}

	public CuentaUsuario getUsuario(){
		return usuario;
	}

	public ArrayList<Mensaje> obtenerMensajesSinLeer() {
		return mensajes.stream().filter(mensaje -> !mensaje.getLeido()).collect(Collectors.toCollection(ArrayList::new));
	}
	public Mensaje obtenerMensajePorIndice(int i) {
		Mensaje mensaje = mensajes.get(i);
		mensaje.marcarComoLeido();
		return mensaje;
	}
	
	public void borrarMensajePorIndice(int i) {
		mensajes.remove(i);
	}
}
