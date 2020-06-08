package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.OperacionEgreso;

public interface CriterioLicitacion {
	public Boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos);
}
