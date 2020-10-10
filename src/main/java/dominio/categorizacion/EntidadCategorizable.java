package dominio.categorizacion;

import java.util.ArrayList;

import dominio.entidades.Organizacion;
import dominio.operaciones.Operacion;
import dominio.categorizacion.exceptions.CategorizacionException;

public class EntidadCategorizable {
	private Operacion operacion;
	private ArrayList<Categoria> categoriasAsociadas;
	
	EntidadCategorizable(Operacion unaOperacion){
		operacion = unaOperacion;
		categoriasAsociadas = new ArrayList<Categoria>();
	}
	
	public String getIdentificador() {
		return this.operacion.getIdentificador();
	}
	
	public boolean esLaEntidad(String identificadorEntidadCategorizable) {
		return this.operacion.esLaOperacion(identificadorEntidadCategorizable);
	}

	public boolean esDeLaOrganizacion(Organizacion unaOrganizacion){
		return this.operacion.esDeLaOrganizacion(unaOrganizacion);
	}

	public boolean esDeLaCategoria(Categoria unaCategoria) {
		return this.categoriasAsociadas.contains(unaCategoria) || 
				this.categoriasAsociadas.stream().anyMatch( categoria -> categoria.esDescendienteDe(unaCategoria));
	}
	
	public boolean tieneCategoriaDelCriterio(CriterioDeCategorizacion unCriterio) {
		return this.categoriasAsociadas.stream().anyMatch( categoria -> categoria.getCriterioDeCategorizacion().equals(unCriterio));
	}
	
	public Categoria categoriaDelCriterio(CriterioDeCategorizacion unCriterio) {
		return this.categoriasAsociadas.stream().filter(categoria -> categoria.getCriterioDeCategorizacion().equals(unCriterio)).findFirst().get();
	}
	
	public void asociarseACategoria(Categoria unaCategoria) throws CategorizacionException {
		this.categoriasAsociadas.add(unaCategoria);
		unaCategoria.seAsociaUnaEntidad();
	}
	
	public void desasociarseACategoria(Categoria unaCategoria) {
		this.categoriasAsociadas.remove(unaCategoria);
		unaCategoria.seDesasociaUnaEntidad();
	}

	public Operacion getOperacion() {
		return operacion;
	}
}
