package dominio.vinculacion;

import java.util.List;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public class VinculacionIngresoEgresos {
	OperacionIngreso ingreso;
	List<OperacionEgreso> egresos;

	public VinculacionIngresoEgresos(OperacionIngreso ingreso){
		this.ingreso = ingreso;
	}

	public void vincularNuevoEgreso(OperacionEgreso egreso) {

	}

	// se comprueba si el egreso puede vincularse con el egreso, para luego llamar al metodo
	// vincularNuevoEgreso (en caso de ser necesario)
	// TODO (si lo utilizamos, agregar metodo en el diagrama)
	public boolean puedeVincularse(OperacionEgreso egreso){
		return true;
	}
	
	public boolean cumpleCondicionObligatoria(CondicionObligatoria condicion) {
		return true;
	}
}
