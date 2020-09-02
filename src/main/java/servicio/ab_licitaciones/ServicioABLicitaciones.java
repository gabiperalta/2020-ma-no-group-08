package servicio.ab_licitaciones;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.OperacionEgreso;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

public class ServicioABLicitaciones {
	
	Licitacion licitacion;
	
	public Licitacion altaLicitacion(OperacionEgreso operacion) {
		//licitacion = new Licitacion(operacion); // TODO revisar, rompe
		return licitacion;
	}
	
	public void altaPresupuesto(Presupuesto presupuesto){
		licitacion.agregarPresupuesto(presupuesto);
	}
	
	public void bajaPresupuesto(Presupuesto presupuesto) {
		licitacion.sacarPresupuesto(presupuesto);
	}
	
	public void suscribir(CuentaUsuario cuentaUsuario) throws Exception {
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_REVISOR")) {
			licitacion.suscribir(cuentaUsuario);
		}
		throw new PermisoDenegadoException("Este Usuario no tiene el permiso de ser revisor");
	}

	public void licitar(String nombreUsuario) throws Exception {
		CuentaUsuario cuentaUsuario = RepositorioUsuarios.getInstance().buscarUsuario(nombreUsuario);
		if (cuentaUsuario.puedeLicitar()) {
			licitacion.licitar();
		} else {
			throw new Exception("el usuario no tiene permisos para licitar");
		}

	}
	
	
}
