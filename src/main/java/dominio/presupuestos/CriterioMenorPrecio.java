package dominio.presupuestos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dominio.operaciones.OperacionEgreso;

public class CriterioMenorPrecio implements CriterioLicitacion{
	
	boolean resultado;

	@Override
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		Collections.sort(presupuestos);
		return true; // TODO corregir return
		// TODO, ACA HAY QUE ARREGLAR CALCULAR EL RESULTADO Y SETEARLO
	}
	
	@Override
	public String descripcion() {
		return resultado?"Criterio de menor precio: Valido":"Criterio de menor precio: Invalido";
	}
		
}
