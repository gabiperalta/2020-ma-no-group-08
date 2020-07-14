package dominio.vinculacion.criterio;

import java.util.ArrayList;
import java.util.List;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public interface CriterioEjecucion {
	
	public Mensaje getResultadosDeVinculacion();
	public void ejecutar(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos);
	public void ordenarIngresos(ArrayList<OperacionIngreso> ingresos);
	public void ordenarEgresos(ArrayList<OperacionEgreso> egresos);
	public void realizarVinculaciones();
}
