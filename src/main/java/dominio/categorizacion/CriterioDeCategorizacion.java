package dominio.categorizacion;

import java.util.ArrayList;
import java.util.List;

import dominio.categorizacion.exceptions.CategorizacionException;

import javax.persistence.*;

@Entity
@Table(name = "criterios_de_categorizacion")
public class CriterioDeCategorizacion {
	@Id @GeneratedValue
	private int id;

	private String nombre;

	@OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "nombre_criterio")
	private List<Categoria> categorias;

	public CriterioDeCategorizacion() {}

	public CriterioDeCategorizacion(String nombreCriterio) throws CategorizacionException{
		this.nombre = nombreCriterio;
		this.categorias = new ArrayList<Categoria>();
	}

	public ArrayList<Categoria> getCategorias(){
		return new ArrayList<>(this.categorias);
	}
	
	public Categoria buscarCategoria(String nombreCategoria) {
		Categoria unaCategoria = this.categorias.stream().filter(categoria -> categoria.getNombre().equals(nombreCategoria)).findFirst().get();
		return unaCategoria;
	}
	
	public void agregarCategoria(String nombreCategoria) throws CategorizacionException {
		if(!this.existeLaCategoria(nombreCategoria)) {
			this.categorias.add(new Categoria(nombreCategoria, this, null));
		}
		else {
			throw new CategorizacionException("Esta categoria ya existe.");
		}
	}
	
	public void agregarCategoria(String nombreCategoria, String nombreCategoriaPadre) throws CategorizacionException {
		if(!this.existeLaCategoria(nombreCategoria)) {
			Categoria categoriaPadre = this.buscarCategoria(nombreCategoriaPadre);
			this.categorias.add(new Categoria(nombreCategoria, this, categoriaPadre));
		}
		else {
			throw new CategorizacionException("Esta categoria ya existe.");
		}
	}
	
	public void quitarCategoria(String nombreCategoria) throws CategorizacionException {
		Categoria categoriaAEliminar = this.buscarCategoria(nombreCategoria);
		if(!categoriaAEliminar.tieneEntidadesAsociadas()) {
			if(!this.tieneCategoriasHijas(categoriaAEliminar)) {
				categoriaAEliminar.desasociarseDelPadre();
				this.categorias.remove(categoriaAEliminar);
			}
			else
				throw new CategorizacionException("Esta Categoria no puede ser eliminada porque tiene categorias hijas.");
		}
		else
			throw new CategorizacionException("Esta Categoria no puede ser eliminada porque hay Entidades asociadas.");
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean existeLaCategoria(String nombreCategoria) {
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
	
	public boolean tieneCategoriasHijas(Categoria unaCategoria) {
		return this.categorias.stream().anyMatch( categoria -> categoria.esDescendienteDe(unaCategoria));
	}
	
	public void asociarCategoriaAEntidadCategorizable(String nombreCategoria, EntidadCategorizable unaEntidad) throws CategorizacionException{
		Categoria unaCategoria = this.buscarCategoria(nombreCategoria);
		if(!unaEntidad.tieneCategoriaDelCriterio(this)) {
			unaEntidad.asociarseACategoria(unaCategoria);
		}
		else {
			Categoria categoriaAnterior = unaEntidad.categoriaDelCriterio(this);
			if(!unaEntidad.esDeLaCategoria(unaCategoria)) {
				if(unaCategoria.esDescendienteDe(categoriaAnterior)) {
					unaEntidad.desasociarseACategoria(categoriaAnterior);
					unaEntidad.asociarseACategoria(unaCategoria);
				}
				else {
					throw new CategorizacionException("La Categoria que quiere asociarse no es valida con una de las que la Entidad ya tenia.");
				}
			}
			else {
				throw new CategorizacionException("La Entidad ya esta asociada a esa Categoria.");
			}
		}
	}
	
	public void desasociarCategoriaAEntidadCategorizable(String nombreCategoria, EntidadCategorizable unaEntidad) throws CategorizacionException{
		Categoria unaCategoria = this.buscarCategoria(nombreCategoria);
		if(unaEntidad.esDeLaCategoria(unaCategoria)) {
			// Si no esta asociada directamente a la categoria, sino a una descendiente de la misma, esta ultima se elimina
			Categoria categoriaADesasociar = unaEntidad.categoriaDelCriterio(this);
			unaEntidad.desasociarseACategoria(categoriaADesasociar);
		}
		else {
			throw new CategorizacionException("Esta Entidad no esta asociada a esa Categoria.");
		}
	}
	
	public boolean puedeBorrarse(){
		return this.categorias.stream().allMatch( categoria -> !categoria.tieneEntidadesAsociadas());
	}
	
}
