package dominio.categorizacion;

import java.util.ArrayList;

import dominio.categorizacion.exceptions.CategorizacionException;

public class EntidadCategorizable {
	private String identificador;
	private ArrayList<Categoria> categoriasAsociadas;
	
	EntidadCategorizable(String identificador){
		this.identificador = identificador;
	}
	
	public String getIdentificador() {
		return this.identificador;
	}
	
	public boolean esLaEntidad(String identificadorEntidadCategorizable) {
		return this.identificador.contentEquals(identificadorEntidadCategorizable);
	}
	
	public boolean esLaCategoria(Categoria unaCategoria) {
		return this.categoriasAsociadas.contains(unaCategoria);
	}
	
	public void vincularseACategoria(Categoria unaCategoria) throws CategorizacionException {
		if(this.puedeVincularseA(unaCategoria)) {
			this.categoriasAsociadas.add(unaCategoria);
		}
		else {
			throw new CategorizacionException("Esta entidad no puede vincularse con esa Categoria");
		}
	}
	
	public void desvincularseDeCategoria(Categoria unaCategoria) throws CategorizacionException {
		if(this.esLaCategoria(unaCategoria)) {
			this.categoriasAsociadas.remove(unaCategoria);
		}
		else {
			throw new CategorizacionException("Esta Entidad no esta vinculada a esa Categoria");
		}
	}
	
	private boolean puedeVincularseA(Categoria unaCategoria) {
		return !categoriasAsociadas.contains(unaCategoria) && 
				this.categoriasAsociadas.stream().allMatch(categoria -> categoria.getCategoriaPadre() != unaCategoria.getCategoriaPadre()
				|| (categoria.getCategoriaPadre() == null && unaCategoria.getCategoriaPadre() == null &&
					categoria.getCriterioDeCategorizacion() == unaCategoria.getCriterioDeCategorizacion())); 
	} // TODO seguramente le falte logica a esta verificacion
	
}
