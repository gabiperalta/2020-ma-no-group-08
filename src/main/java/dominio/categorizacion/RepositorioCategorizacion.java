package dominio.categorizacion;

import java.util.ArrayList;

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
																			equals(nombreCriterioDeCategorizacion)).findFirst().get();
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
	
	public void quitarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		CriterioDeCategorizacion criterioABorrar = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		this.criteriosDeCategorizacion.remove(criterioABorrar);
	}
	
	public boolean existeElCriterio(CriterioDeCategorizacion criterioDeCategorizacion) {
		boolean existiaElCriterio;
		try {
			this.buscarCriterioDeCategorizacion(criterioDeCategorizacion.getNombre());
			existiaElCriterio = true;
		}
		catch (Exception NoSuchElementException){
			existiaElCriterio = false;
		}
		return existiaElCriterio;
	}
	
	// Entidades Categorizables
	public EntidadCategorizable buscarEntidadCategorizable(String identificadorEntidadCategorizable) {
		EntidadCategorizable unaEntidadCategorizable = entidadesCategorizables.stream().filter(entidad -> entidad.getIdentificador().
				equals(identificadorEntidadCategorizable)).findFirst().get();
		if(unaEntidadCategorizable == null) {
			if(identificadorEntidadCategorizable.startsWith("OE")) { // OE por Operacion Egreso
				OperacionEgreso unaOperacionEgreso = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
				unaEntidadCategorizable = new EntidadCategorizable(unaOperacionEgreso.getIdentificador());
			}
			else
				if(identificadorEntidadCategorizable.startsWith("OI")) { // OI por Operacion Ingreso
					OperacionIngreso unaOperacionIngreso = RepoOperacionesIngreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
					unaEntidadCategorizable = new EntidadCategorizable(unaOperacionIngreso.getIdentificador());
				}
				else
					throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
		}
		return unaEntidadCategorizable;
	}
	
	public void vincularCriterioAEntidadCategorizable(String identificadorEntidadCategorizable, Categoria unaCategoria) throws CategorizacionException {
		EntidadCategorizable unaEntidadCategorizable = this.buscarEntidadCategorizable(identificadorEntidadCategorizable);
		unaEntidadCategorizable.vincularseACategoria(unaCategoria);
	}
	
}
