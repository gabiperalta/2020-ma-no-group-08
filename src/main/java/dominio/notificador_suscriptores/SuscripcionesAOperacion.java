package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.OperacionEgreso;

public class SuscripcionesAOperacion {
	OperacionEgreso operacion_egreso;
	ArrayList<CuentaUsuario> usuarios;
	
	public SuscripcionesAOperacion(OperacionEgreso unaOperacionEgreso) {
		operacion_egreso = unaOperacionEgreso;
		usuarios = new ArrayList<CuentaUsuario>();
	}
	
	public OperacionEgreso getOperacionEgreso(){
		return operacion_egreso;
	}
	
	public ArrayList<CuentaUsuario> getUsuarios(){
		return usuarios;
	}
	
	public void suscribir(CuentaUsuario usuario) {
		usuarios.add(usuario);
	}
	
	public void desuscribir(CuentaUsuario usuario) {
		usuarios.remove(usuario);
	}
	
}
