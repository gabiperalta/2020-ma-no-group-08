package dominio.vinculacion.criterio;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.vinculacion.VinculacionIngresoEgresos;

import java.util.ArrayList;
import java.util.List;

public class OrdenValorPrimerEgreso implements CriterioEjecucion {

	List<VinculacionIngresoEgresos> resultadosDeVinculacion;
	boolean vinculacionCorrecta;

	public OrdenValorPrimerEgreso(){
		resultadosDeVinculacion = new ArrayList<>();
	}

	@Override
	public Mensaje getResultadosDeVinculacion() {
		return null;
	}

	@Override
	public void ejecutar(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos) {
		this.ordenarIngresos(ingresos);
		this.ordenarEgresos(egresos);
		this.realizarVinculaciones(ingresos,egresos);
	}

	@Override
	public void ordenarIngresos(List<OperacionIngreso> ingresos) {
		ingresos.sort(new OrdenarIngresosPorValor());
	}

	@Override
	public void ordenarEgresos(List<OperacionEgreso> egresos) {
		egresos.sort(new OrdenarEgresosPorValor());
	}

	@Override
	public void realizarVinculaciones(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos) {
		ingresos.forEach(ingreso -> {
			VinculacionIngresoEgresos vinculacionIngresoEgresos = new VinculacionIngresoEgresos(ingreso);
			egresos.forEach(egreso -> {
				if(vinculacionIngresoEgresos.puedeVincularse(egreso)) {
					vinculacionIngresoEgresos.vincularNuevoEgreso(egreso);
					egresos.remove(egreso);
				}
			});
			resultadosDeVinculacion.add(vinculacionIngresoEgresos);
			// TODO en que casos la vinculacion es correcta?
		});
	}

}
