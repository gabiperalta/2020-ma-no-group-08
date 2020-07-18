package dominio.vinculacion.criterio;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

import java.util.ArrayList;
import java.util.List;

public class Fecha implements CriterioEjecucion {

	@Override
	public Mensaje getResultadosDeVinculacion() {
		return null;
	}

	@Override
	public void ejecutar(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos) {

	}

    @Override
    public void ordenarIngresos(List<OperacionIngreso> ingresos) {

    }

    @Override
    public void ordenarEgresos(List<OperacionEgreso> egresos) {

    }

    @Override
    public void realizarVinculaciones(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos) {

    }

}
