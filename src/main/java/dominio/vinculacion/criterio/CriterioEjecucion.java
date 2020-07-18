package dominio.vinculacion.criterio;

import java.util.ArrayList;
import java.util.List;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public interface CriterioEjecucion {
	
	public Mensaje getResultadosDeVinculacion();
	public void ejecutar(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos);
	public void ordenarIngresos(List<OperacionIngreso> ingresos);
	public void ordenarEgresos(List<OperacionEgreso> egresos);
	public void realizarVinculaciones(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos);
}
