package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.OperacionEgreso;
import dominio.presupuestos.Licitacion;

public class Suscripciones {
	Licitacion licitacion;
	ArrayList<CuentaUsuario> usuarios;
	
	public Suscripciones(Licitacion lic) {
		this.licitacion = lic;
		usuarios = new ArrayList<CuentaUsuario>();
	}
	
	public void suscribirse(CuentaUsuario usuario) {
		usuarios.add(usuario);
	}
	
}
