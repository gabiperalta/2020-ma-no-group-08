package dominio.presupuestos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dominio.operaciones.OperacionEgreso;

public class CriterioMenorPrecio implements CriterioLicitacion{

	@Override
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		Collections.sort(presupuestos);
		return true; // TODO corregir return
	}
		
}
