package servicio.abm_categorizaciones;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.RepositorioCategorizacion;
import dominio.categorizacion.exceptions.CategorizacionException;

public class ServicioABMCategorizaciones {
	private Categoria categoriaActual;
	private CriterioDeCategorizacion criterioDeCategorizacionActual;
	
	public void altaCriterioCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		this.criterioDeCategorizacionActual = new CriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void bajaCriterioCategorizacion(String nombreCriterioDeCategorizacion) {
		this.criterioDeCategorizacionActual = null;
		RepositorioCategorizacion.getInstance().quitarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
	}
	
	public void buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		this.criterioDeCategorizacionActual = RepositorioCategorizacion.getInstance().buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		this.categoriaActual = null;
	}
	
	public void asociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().asociarCriterioAEntidadCategorizable(identificadorEntidadCategorizable, this.categoriaActual);
	}
	
	public void desasociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable) throws CategorizacionException {
		RepositorioCategorizacion.getInstance().desasociarCriterioAEntidadCategorizable(identificadorEntidadCategorizable, this.categoriaActual);
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
	
	public void eliminarCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.criterioDeCategorizacionActual != null)
			this.criterioDeCategorizacionActual.quitarCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ninguna Categoria referenciada, debes buscar una");
	}
	
	public void eliminarSubCategoria(String nombreCategoria) throws CategorizacionException {
		if(this.categoriaActual != null)
			this.categoriaActual.quitarSubCategoria(nombreCategoria);
		else
			throw new CategorizacionException("No hay ninguna Categoria referenciada, debes buscar una");
	}
	
	public Categoria getCategoriaActual() {
		return this.categoriaActual;
	}
	
	public CriterioDeCategorizacion getCriterioDeCategorizacionActual() {
		return this.criterioDeCategorizacionActual;
	}
	
}
