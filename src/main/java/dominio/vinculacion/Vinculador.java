package dominio.vinculacion;

import java.util.List;
import java.util.stream.Collectors;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;
import dominio.vinculacion.criterio.CriterioEjecucion;
import seguridad.sesion.SesionEstandar;


public class Vinculador {
	List<CondicionObligatoria> condicionesObligatorias;
	CriterioEjecucion criterio;
	SesionEstandar usuario;
	
	public void vincular() {
		RepoOperacionesEgreso repoEgreso = RepoOperacionesEgreso.getInstance();
		RepoOperacionesIngreso repoIngreso = RepoOperacionesIngreso.getInstance();
		List<OperacionEgreso> egresos = repoEgreso.getOperacionesEgreso()
											.stream()
											.filter(e->e.getEntidadDestino() == usuario.getOrganizacion())
											.collect(Collectors.toList());
		List<OperacionIngreso> ingresos = repoIngreso.getIngresos()
											.stream()
											.filter(i->i.getEntidadOrigen() == usuario.getOrganizacion())
											.collect(Collectors.toList());
		criterio.ejecutar(ingresos, egresos);
	}
}
