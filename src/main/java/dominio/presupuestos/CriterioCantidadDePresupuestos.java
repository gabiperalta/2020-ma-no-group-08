package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.OperacionEgreso;

public class CriterioCantidadDePresupuestos implements CriterioLicitacion{
	
	private int presupuestosNecesarios;
	@Override 
	public Boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		return this.presupuestosNecesarios == operacion.getPresupuestosNecesarios();
	}
}
