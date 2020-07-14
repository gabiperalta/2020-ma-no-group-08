package dominio.vinculacion;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public class Mix implements CriterioEjecucion {

	@Override
	public Mensaje obtenerResultadosDeVinculacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ejecutar(OperacionIngreso ingresos, OperacionEgreso egresos) {
		// TODO Auto-generated method stub

	}

}
