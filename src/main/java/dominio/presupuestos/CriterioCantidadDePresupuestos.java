package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.OperacionEgreso;

public class CriterioCantidadDePresupuestos implements CriterioLicitacion{
	
	private int presupuestosNecesarios;
	boolean resultado;

	@Override 
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		resultado = this.presupuestosNecesarios == operacion.getPresupuestosNecesarios();
		return resultado;
	}

	@Override
	public String descripcion() {
		String descripcion;
		if(resultado) {
			descripcion = "Criterio de cantidad de presupuestos: Valido";
		}
		else {
			descripcion = "Criterio de cantidad de presupuestos: Invalido";
		}
		
		return descripcion;
	}
}
