package dominio.notificador_suscriptores;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;

public class Suscripciones {
	Licitacion licitacion;
	ArrayList<CuentaUsuario> usuarios;
	
	public Suscripciones(Licitacion lic) {
		this.licitacion = lic;
		usuarios = new ArrayList<>();
	}
	
	public void agregarSuscriptor(CuentaUsuario usuario) {
		usuarios.add(usuario);
	}

	public Licitacion getLicitacion(){
		return this.licitacion;
	}

	public ArrayList<CuentaUsuario> getUsuarios(){
		return this.usuarios;
	}
}
