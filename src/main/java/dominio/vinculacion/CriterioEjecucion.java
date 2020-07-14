package dominio.vinculacion;

import dominio.notificador_suscriptores.Mensaje;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

public interface CriterioEjecucion {
	
	public Mensaje obtenerResultadosDeVinculacion();
	public void ejecutar(OperacionIngreso ingresos, OperacionEgreso egresos);
}
