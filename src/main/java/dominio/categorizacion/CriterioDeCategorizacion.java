package dominio.categorizacion;

import java.util.ArrayList;

import dominio.categorizacion.exceptions.CategorizacionException;

public class CriterioDeCategorizacion {
	
	private String nombre;
	private ArrayList<Categoria> categorias;
	
	public CriterioDeCategorizacion(String nombreCriterio) throws CategorizacionException{
		this.nombre = nombreCriterio;
		this.categorias = new ArrayList<Categoria>();
		RepositorioCategorizacion.getInstance().agregarCriterioDeCategorizacion(this);
	}
	
	public Categoria agregarCategoria(String nombreCategoria) throws CategorizacionException {
		if(!this.existeELaCategoria(nombreCategoria)) {
			Categoria unaCategoria = new Categoria(nombreCategoria, null, this);
			this.categorias.add(unaCategoria);
			return unaCategoria;
		}
		else {
			throw new CategorizacionException("Esta categoria ya existe a este nivel de categorizacion.");
		}
		
	}
	
	public void quitarCategoria(String nombreCategoria) {
		Categoria categoriaAEliminar = this.buscarCategoria(nombreCategoria);
		this.categorias.remove(categoriaAEliminar);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean existeELaCategoria(String nombreCategoria) {
		boolean existiaLaCategoria;
		try {
			this.buscarCategoria(nombreCategoria);
			existiaLaCategoria = true;
		}
		catch (Exception NoSuchElementException){
			existiaLaCategoria = false;
		}
		return existiaLaCategoria;
	}
	
	public Categoria buscarCategoria(String nombreCategoria) {
		Categoria unaCategoria = this.categorias.stream().filter(categoria -> categoria.getNombre().equals(nombreCategoria)).findFirst().get();
		return unaCategoria;
	}
	
	
}
