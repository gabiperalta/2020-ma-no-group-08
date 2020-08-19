package dominio.categorizacion;

import java.util.ArrayList;

import dominio.categorizacion.exceptions.CategorizacionException;

public class EntidadCategorizable {
	private String identificador;
	private ArrayList<Categoria> categoriasAsociadas;
	
	EntidadCategorizable(String identificador){
		this.identificador = identificador;
		categoriasAsociadas = new ArrayList<Categoria>();
	}
	
	public String getIdentificador() {
		return this.identificador;
	}
	
	public boolean esLaEntidad(String identificadorEntidadCategorizable) {
		return this.identificador.contentEquals(identificadorEntidadCategorizable);
	}
	
	public boolean esDeLaCategoria(Categoria unaCategoria) {
		return this.categoriasAsociadas.contains(unaCategoria);
	}
	
	public void asociarseACategoria(Categoria unaCategoria) throws CategorizacionException {
		if(this.puedeVincularseA(unaCategoria)) {
			this.categoriasAsociadas.add(unaCategoria);
		}
		else {
			throw new CategorizacionException("Esta entidad no puede vincularse con esa Categoria");
		}
	}
	
	public void desasociarseDeCategoria(Categoria unaCategoria) throws CategorizacionException {
		if(this.esDeLaCategoria(unaCategoria)) {
			this.categoriasAsociadas.remove(unaCategoria);
		}
		else {
			throw new CategorizacionException("Esta Entidad no esta vinculada a esa Categoria");
		}
	}
	
	private boolean puedeVincularseA(Categoria unaCategoria) {
		return !this.categoriasAsociadas.contains(unaCategoria) && 
				this.categoriasAsociadas.stream().allMatch(categoria -> categoria.getCategoriaPadre() != unaCategoria.getCategoriaPadre()
				|| (categoria.getCategoriaPadre() == null && unaCategoria.getCategoriaPadre() == null &&
					categoria.getCriterioDeCategorizacion() == unaCategoria.getCriterioDeCategorizacion())); 
	} // TODO seguramente le falte logica a esta verificacion
	
}
