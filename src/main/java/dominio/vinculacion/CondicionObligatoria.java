package dominio.vinculacion;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public interface CondicionObligatoria {
	
	public boolean cumpleCondicion(OperacionIngreso ingreso, OperacionEgreso egreso);
}
