package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.OperacionEgreso;

public interface CriterioLicitacion {
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos);
	public String descripcion();
}
