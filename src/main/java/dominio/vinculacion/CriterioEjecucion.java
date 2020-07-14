package dominio.vinculacion;

import java.util.List;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public interface CriterioEjecucion {
	
	public Mensaje obtenerResultadosDeVinculacion();
	public void ejecutar(List<OperacionIngreso> ingresos, List<OperacionEgreso> egresos);
}
