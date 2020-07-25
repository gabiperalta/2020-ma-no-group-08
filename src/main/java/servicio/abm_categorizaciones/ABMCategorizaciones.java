package servicio.abm_categorizaciones;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.RepositorioCategorizacion;
import dominio.categorizacion.exceptions.CategorizacionException;

public class ABMCategorizaciones {
	private Categoria categoriaActual;
	private CriterioDeCategorizacion criterioDeCategorizacionActual;
	
	public void altaCriterioCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		this.criterioDeCategorizacionActual = new CriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void bajaCriterioCategorizacion(String nombreCriterioDeCategorizacion) {
		RepositorioCategorizacion.getInstance().quitarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		this.criterioDeCategorizacionActual = RepositorioCategorizacion.getInstance().buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void asociarCategoriaAEntidadCategorizable(String nombreEntidadCategorizable) {
		
	}
	
	public void agregarCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.criterioDeCategorizacionActual != null)
			this.categoriaActual = criterioDeCategorizacionActual.agregarCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ningun Criterio de Categorizacion referenciado, debes buscar o crear uno");
	}
	
	public void agregarSubCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.categoriaActual != null)
			this.categoriaActual.agregarSubCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ninguna Categoria referenciada, debes buscar una");
	}
	
	public void buscarCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.criterioDeCategorizacionActual != null)
			this.categoriaActual = this.criterioDeCategorizacionActual.buscarCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ningun Criterio de Categorizacion referenciado, debes buscar o crear uno");
	}
	
	public void buscarSubCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.categoriaActual != null)
			this.categoriaActual = this.categoriaActual.buscarSubCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ninguna Categoria referenciada, debes buscar una");
	}
	
	public void eliminarCategoria(String nombreCategoria) {
		this.criterioDeCategorizacionActual.quitarCategoria(nombreCategoria);
	}
	
	public void eliminarSubCategoria(String nombreCategoria) {
		this.categoriaActual.quitarSubCategoria(nombreCategoria);
	}
	
	public Categoria getCategoriaActual() {
		return this.categoriaActual;
	}
	
	public CriterioDeCategorizacion getCriterioDeCategorizacionActual() {
		return this.criterioDeCategorizacionActual;
	}
	
}
