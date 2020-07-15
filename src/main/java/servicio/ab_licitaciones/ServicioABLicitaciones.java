package servicio.ab_licitaciones;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.OperacionEgreso;
import seguridad.sesion.exceptions.PermisoDenegadoException;

public class ServicioABLicitaciones {
	
	Licitacion licitacion;
	
	public Licitacion altaLicitacion(OperacionEgreso operacion, int presupuestosNecesarios) {
		licitacion = new Licitacion(operacion, presupuestosNecesarios);
		return licitacion;
	}
	
	public void altaPresupuesto(Presupuesto presupuesto){
		licitacion.agregarPresupuesto(presupuesto);
	}
	
	public void bajaPresupuesto(Presupuesto presupuesto) {
		licitacion.sacarPresupuesto(presupuesto);
	}
	
	public void suscribir(CuentaUsuario cuentaUsuario) throws PermisoDenegadoException {
		if(cuentaUsuario.tieneElPrivilegio("PRIVILEGIO_REVISOR")) {
			licitacion.suscribir(cuentaUsuario);
		}
		throw new PermisoDenegadoException("Este Usuario no tiene el permiso de ser revisor");
	}
	
	
}
