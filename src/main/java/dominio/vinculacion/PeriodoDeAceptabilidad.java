package dominio.vinculacion;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public class PeriodoDeAceptabilidad implements CondicionObligatoria {

	@Override
	public boolean cumpleCondicion(OperacionIngreso ingreso, OperacionEgreso egreso) {
		return false;
	}

}
