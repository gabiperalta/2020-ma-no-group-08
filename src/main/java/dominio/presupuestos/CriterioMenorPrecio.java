package dominio.presupuestos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dominio.operaciones.OperacionEgreso;

public class CriterioMenorPrecio implements CriterioLicitacion{

	@Override
	public int validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		Collections.sort(presupuestos, new Comparator()){
			@Override
			public int compare(Presupuesto p1, Presupuesto p2) {
				return new Integer(p1.getMontoTotal().compareTo(new Integer(p2.getMontoTotal()));
			}
		}
		
	}
		
}
