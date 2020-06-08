package dominio.presupuestos;

import java.util.ArrayList;
import java.util.List;

import dominio.operaciones.OperacionEgreso;

public interface CriterioLicitacion {
	public Presupuesto validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos);
}
