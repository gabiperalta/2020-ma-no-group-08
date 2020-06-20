package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.OperacionEgreso;
import dominio.presupuestos.Licitacion;

public class SuscripcionesAOperacion {
	Licitacion licitacion;
	ArrayList<CuentaUsuario> usuarios;
	
	public SuscripcionesAOperacion(Licitacion lic) {
		this.licitacion = lic;
		usuarios = new ArrayList<CuentaUsuario>();
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
