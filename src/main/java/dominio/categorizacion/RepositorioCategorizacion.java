package dominio.categorizacion;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;

public class RepositorioCategorizacion {
	
	private ArrayList<CriterioDeCategorizacion> criteriosDeCategorizacion;
	
	private ArrayList<EntidadCategorizable> entidadesCategorizables;
	
	// singleton
	private static class RepositorioCategorizacionHolder {		
        static final RepositorioCategorizacion singleInstanceRepositorioCategorizacion = new RepositorioCategorizacion();
    }
	
	public static RepositorioCategorizacion getInstance() {
        return RepositorioCategorizacionHolder.singleInstanceRepositorioCategorizacion;
    }
	
	public RepositorioCategorizacion() {
		this.criteriosDeCategorizacion = new ArrayList<CriterioDeCategorizacion>();
		this.entidadesCategorizables = new ArrayList<EntidadCategorizable>();
	}
	
	// Criterios De Categorizacion
	public CriterioDeCategorizacion buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		CriterioDeCategorizacion unCriterioDeCategorizacion = criteriosDeCategorizacion.stream().filter(criterio -> criterio.getNombre().
																			contentEquals(nombreCriterioDeCategorizacion)).findFirst().get();
		return unCriterioDeCategorizacion;
	}
	
	public void agregarCriterioDeCategorizacion(CriterioDeCategorizacion criterioDeCategorizacion) throws CategorizacionException {
		if(!this.existeElCriterio(criterioDeCategorizacion)) {
			this.criteriosDeCategorizacion.add(criterioDeCategorizacion);
		}
		else {
			throw new CategorizacionException("Este Criterio De Categorizacion ya existe.");
		}
	}
	
	public void quitarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		CriterioDeCategorizacion criterioABorrar = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		if(criterioABorrar.puedeBorrarse()) {
			this.criteriosDeCategorizacion.remove(criterioABorrar);
		}
		else {
			throw new CategorizacionException("Este Criterio de Categorizacion no puede ser eliminado porque hay Entidades Categorizables asociadas al mismo.");
		}
				
	}
	
	private boolean existeElCriterio(CriterioDeCategorizacion criterioDeCategorizacion) {
		try {
			this.buscarCriterioDeCategorizacion(criterioDeCategorizacion.getNombre());
			return true;
		}
		catch (Exception NoSuchElementException){
			return false;
		}
	}
	
	// Entidades Categorizables
	// TODO: Dependiendo de como terminemos normalizando la BD de Operaciones, vamos a tener que hacer la distincion entre OperacionEgreso y OperacionIngreso o no.
	public EntidadCategorizable buscarEntidadCategorizable(String identificadorEntidadCategorizable) throws CategorizacionException {
		EntidadCategorizable unaEntidadCategorizable = entidadesCategorizables.stream().filter(entidad -> entidad.getIdentificador().
				equals(identificadorEntidadCategorizable)).findFirst().get();
		if(unaEntidadCategorizable == null) {
			if(identificadorEntidadCategorizable.startsWith("OE")) { // OE por Operacion Egreso
				OperacionEgreso operacionEgreso = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
				unaEntidadCategorizable = new EntidadCategorizable(operacionEgreso);
			}
			else
				if(identificadorEntidadCategorizable.startsWith("OI")) { // OI por Operacion Ingreso
					OperacionIngreso operacionIngreso = RepoOperacionesIngreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
					unaEntidadCategorizable = new EntidadCategorizable(operacionIngreso);
				}
				else
					throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
		}
		return unaEntidadCategorizable;
	}
	
	public void asociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable, String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		CriterioDeCategorizacion unCriterio = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		EntidadCategorizable unaEntidad = this.buscarEntidadCategorizable(identificadorEntidadCategorizable);
		unCriterio.asociarCategoriaAEntidadCategorizable(nombreCategoria, unaEntidad);
	}
	
	public void desasociarCategoriaAEntidadCategorizable(String identificadorEntidadCategorizable, String nombreCategoria, String nombreCriterioDeCategorizacion) throws CategorizacionException {
		CriterioDeCategorizacion unCriterio = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		EntidadCategorizable unaEntidad = this.buscarEntidadCategorizable(identificadorEntidadCategorizable);
		unCriterio.desasociarCategoriaAEntidadCategorizable(nombreCategoria, unaEntidad);
	}
	
	public ArrayList<EntidadCategorizable> filtrarEntidadesDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);
		// transformo el Stream en un ArrayList
		return new ArrayList<EntidadCategorizable>(this.entidadesCategorizables.stream().filter( entidad -> entidad.esDeLaCategoria(unaCategoria)).collect(Collectors.toList()));
	}
	
}
