package dominio.categorizacion;

import java.util.ArrayList;

import dominio.categorizacion.exceptions.CategorizacionException;

public class Categoria {
	
	private String nombre;
	private Categoria categoriaPadre;
	private ArrayList<Categoria> subCategorias;
	private CriterioDeCategorizacion criterioDeCategorizacion;
	
	public Categoria(String nombreCategoria, CriterioDeCategorizacion criterioDeCategorizacion){
		this.nombre = nombreCategoria;
		this.categoriaPadre = null;
		this.subCategorias = new ArrayList<Categoria>();
		this.criterioDeCategorizacion = criterioDeCategorizacion;
	}
	
	public Categoria(String nombreCategoria, Categoria categoriaPadre, CriterioDeCategorizacion criterioDeCategorizacion){
		this.nombre = nombreCategoria;
		this.categoriaPadre = categoriaPadre;
		this.subCategorias = new ArrayList<Categoria>();
		this.criterioDeCategorizacion = criterioDeCategorizacion;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Categoria getCategoriaPadre() {
		return this.categoriaPadre;
	}
	
	public CriterioDeCategorizacion getCriterioDeCategorizacion() {
		return criterioDeCategorizacion;
	}
	
	public Categoria agregarSubCategoria(String nombreSubCriterio) throws CategorizacionException {
		if(this.existeELaCategoria(nombreSubCriterio)) {
			Categoria unaCategoria = new Categoria(nombreSubCriterio, this, this.criterioDeCategorizacion);
			this.subCategorias.add(unaCategoria);
			return unaCategoria;
		}
		else {
			throw new CategorizacionException("Esta categoria ya existe a este nivel de categorizacion.");
		}
	}
	
	public void quitarSubCategoria(String nombreCategoria) {
		Categoria categoriaAEliminar = this.buscarSubCategoria(nombreCategoria);
		this.subCategorias.remove(categoriaAEliminar);
	}
	
	public boolean existeELaCategoria(String nombreCategoria) {
		boolean existiaLaCategoria;
		try {
			this.buscarSubCategoria(nombreCategoria);
			existiaLaCategoria = true;
		}
		catch (Exception NoSuchElementException){
			existiaLaCategoria = false;
		}
		return existiaLaCategoria;
	}
	
	public Categoria buscarSubCategoria(String nombreCategoria) {
		Categoria unaCategoria = this.subCategorias.stream().filter(categoria -> categoria.getNombre().equals(nombreCategoria)).findFirst().get();
		return unaCategoria;
	}
	
}
