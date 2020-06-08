package dominio.presupuestos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dominio.operaciones.OperacionEgreso;

public class CriterioMenorPrecio implements CriterioLicitacion{

	@Override
	public Presupuesto validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		List<Presupuesto> presupuestosOrdenados = Collections.sort(presupuestos, (p1,p2)->p1.compareTo(p2));
		return presupuestosOrdenados.get(0);
	}
	
}
