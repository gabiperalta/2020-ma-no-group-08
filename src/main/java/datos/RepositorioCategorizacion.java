package datos;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

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

	public ArrayList<CriterioDeCategorizacion> getCriteriosDeCategorizacion(){
		return this.criteriosDeCategorizacion;
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

	private EntidadCategorizable buscarEntidadEntreLasYaCategorizadas(String identificadorEntidadCategorizable){
		return entidadesCategorizables.stream().filter(entidad -> entidad.getIdentificador().
				equals(identificadorEntidadCategorizable)).findFirst().get();
	}

	private boolean existeEntidadEntreLasCategorizadas(String identificadorEntidadCategorizable) {
		boolean existiaLaEntidad;
		try {
			this.buscarEntidadEntreLasYaCategorizadas(identificadorEntidadCategorizable);
			existiaLaEntidad = true;
		} catch (Exception NoSuchElementException) {
			existiaLaEntidad = false;
		}
		return existiaLaEntidad;
	}
	
	// Entidades Categorizables
	// TODO: Dependiendo de como terminemos normalizando la BD de Operaciones, vamos a tener que hacer la distincion entre OperacionEgreso y OperacionIngreso o no.
	public EntidadCategorizable buscarEntidadCategorizable(String identificadorEntidadCategorizable) throws CategorizacionException {
		EntidadCategorizable unaEntidadCategorizable;
		if(existeEntidadEntreLasCategorizadas(identificadorEntidadCategorizable)){
			unaEntidadCategorizable = buscarEntidadEntreLasYaCategorizadas(identificadorEntidadCategorizable);
		}
		else {
			if(identificadorEntidadCategorizable.startsWith("OE")) { // OE por Operacion Egreso
				OperacionEgreso operacionEgreso = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
				unaEntidadCategorizable = new EntidadCategorizable(operacionEgreso);
			}
			else
				if(identificadorEntidadCategorizable.startsWith("OI")) { // OI por Operacion Ingreso
					OperacionIngreso operacionIngreso = RepoOperacionesIngreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
					unaEntidadCategorizable = new EntidadCategorizable(operacionIngreso);
				}
				else{
					if(identificadorEntidadCategorizable.startsWith("L")){
						int largoIdentificador = identificadorEntidadCategorizable.length();
						String identificadorLicitacion = identificadorEntidadCategorizable.substring(0,largoIdentificador - 3);
						String identificadorPresupuesto = identificadorEntidadCategorizable.substring(largoIdentificador - 2, largoIdentificador);
						Licitacion licitacion = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(identificadorLicitacion);
						Presupuesto presupuesto = licitacion.getPresupuestos().stream().filter( unPresupuesto -> unPresupuesto.getIdentificador().endsWith(identificadorPresupuesto)).
														findFirst().get();
						unaEntidadCategorizable = new EntidadCategorizable(presupuesto);
					}
					else
						throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
				}
			this.entidadesCategorizables.add(unaEntidadCategorizable);
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
	
	public ArrayList<EntidadCategorizable> filtrarEntidadesDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);

		return new ArrayList<EntidadCategorizable>(this.entidadesCategorizables.stream().
				filter( entidad -> entidad.esDeLaCategoria(unaCategoria) && entidad.esDeLaOrganizacion(unaOrganizacion)).collect(Collectors.toList()));
	}

	public ArrayList<EntidadCategorizable> filtrarEgresosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return new ArrayList<EntidadCategorizable>(this.filtrarEntidadesDeLaCategoria(nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion).stream().
						filter( entidad -> entidad.getIdentificador().startsWith("OE")).collect(Collectors.toList()));
	}

	public ArrayList<EntidadCategorizable> filtrarIngresosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return new ArrayList<EntidadCategorizable>(this.filtrarEntidadesDeLaCategoria(nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion).stream().
						filter( entidad -> entidad.getIdentificador().startsWith("OI")).collect(Collectors.toList()));
	}

	public ArrayList<EntidadCategorizable> filtrarPresupuestosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return new ArrayList<EntidadCategorizable>(this.filtrarEntidadesDeLaCategoria(nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion).stream().
				filter( entidad -> entidad.getIdentificador().startsWith("L")).collect(Collectors.toList()));
	}
}