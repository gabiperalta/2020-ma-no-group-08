package servicio.abm_categorizaciones;

import java.util.ArrayList;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.categorizacion.RepositorioCategorizacion;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.entidades.Organizacion;

public class ServicioABMCategorizaciones {
	
	public void altaCriterioCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().agregarCriterioDeCategorizacion(new CriterioDeCategorizacion(nombreCriterioDeCategorizacion));
	}
	
	public void bajaCriterioCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().quitarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public CriterioDeCategorizacion buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		return RepositorioCategorizacion.getInstance().buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void agregarCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).agregarCategoria(nombreCategoria);
	}
	
	public void agregarCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, String nombreCategoriaPadre) throws CategorizacionException {
		this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).agregarCategoria(nombreCategoria, nombreCategoriaPadre);
	}
	
	public Categoria buscarCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		return this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);
	}
	
	public void eliminarCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).quitarCategoria(nombreCategoria);
	}
	
	public void asociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable, String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().asociarCategoriaAEntidadCategorizable(identificadorEntidadCategorizable, nombreCategoria,nombreCriterioDeCategorizacion);
	}
	
	public void desasociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable, String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().desasociarCategoriaAEntidadCategorizable(identificadorEntidadCategorizable, nombreCategoria, nombreCriterioDeCategorizacion);
	}
	
	public ArrayList<EntidadCategorizable> filtrarEntidadesDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return RepositorioCategorizacion.getInstance().filtrarEntidadesDeLaCategoria( nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion
		);
	}

	public ArrayList<CriterioDeCategorizacion> listarCriteriosDeCategorizacion(){
		return RepositorioCategorizacion.getInstance().getCriteriosDeCategorizacion();
	}
	
}
