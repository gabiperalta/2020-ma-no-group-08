package datos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import mock.ServerDataMock;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioCategorizacion {
	
	private ArrayList<CriterioDeCategorizacion> criteriosDeCategorizacion;
	
	private ArrayList<EntidadCategorizable> entidadesCategorizables;

	EntityManager entityManager;

	public RepositorioCategorizacion(EntityManager em){
		entityManager = em;
	}


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
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<CriterioDeCategorizacion> consulta = cb.createQuery(CriterioDeCategorizacion.class);
		Root<CriterioDeCategorizacion> criteriosDeCategorizacion = consulta.from(CriterioDeCategorizacion.class);
		return new ArrayList<>(this.entityManager.createQuery(consulta.select(criteriosDeCategorizacion)).getResultList());
	}
	
	// Criterios De Categorizacion
	public CriterioDeCategorizacion buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {
		return entityManager.find(CriterioDeCategorizacion.class, nombreCriterioDeCategorizacion);
	}
	
	public void agregarCriterioDeCategorizacion(CriterioDeCategorizacion criterioDeCategorizacion) throws CategorizacionException {
		if(!this.existeElCriterio(criterioDeCategorizacion)) {
			this.entityManager.persist(criterioDeCategorizacion);
		}
		else {
			throw new CategorizacionException("Este Criterio De Categorizacion ya existe.");
		}
	}
	
	public void quitarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) throws CategorizacionException {
		CriterioDeCategorizacion criterioABorrar = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion);
		if(this.existeElCriterio(criterioABorrar)){
			if(criterioABorrar.puedeBorrarse()) {
				entityManager.remove(criterioABorrar);
			}
			else {
				throw new CategorizacionException("Este Criterio de Categorizacion no puede ser eliminado porque hay Entidades Categorizables asociadas al mismo.");
			}
		}
	}
	
	private boolean existeElCriterio(CriterioDeCategorizacion criterioDeCategorizacion) {
		return entityManager.contains(criterioDeCategorizacion);
	}

	private EntidadCategorizable buscarEntidadEntreLasYaCategorizadas(String identificadorEntidadCategorizable){
		return entityManager.find(EntidadCategorizable.class, identificadorEntidadCategorizable);
	}

	private boolean existeEntidadEntreLasCategorizadas(String identificadorEntidadCategorizable) {
		return entityManager.contains(this.buscarEntidadEntreLasYaCategorizadas(identificadorEntidadCategorizable));
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
				RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(Persistence.createEntityManagerFactory("db").createEntityManager()) ;
				OperacionEgreso operacionEgreso = repoOperacionesEgreso.buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorEntidadCategorizable);
				unaEntidadCategorizable = new EntidadCategorizable(operacionEgreso);
			}
			else
			if(identificadorEntidadCategorizable.startsWith("L")){
				int largoIdentificador = identificadorEntidadCategorizable.length();
				RepoLicitaciones repoLicitaciones = new RepoLicitaciones(ServerDataMock.getEntityManager());
				String identificadorLicitacion = identificadorEntidadCategorizable.substring(0,largoIdentificador - 3);
				String identificadorPresupuesto = identificadorEntidadCategorizable.substring(largoIdentificador - 2, largoIdentificador);
				Licitacion licitacion = repoLicitaciones.buscarLicitacionPorIdentificador(identificadorLicitacion);
				Presupuesto presupuesto = licitacion.getPresupuestos().stream().filter( unPresupuesto -> unPresupuesto.getIdentificador().endsWith(identificadorPresupuesto)).
						findFirst().get();
				unaEntidadCategorizable = new EntidadCategorizable(presupuesto);
			}
			else
				throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
				/*if(identificadorEntidadCategorizable.startsWith("OI")) { // OI por Operacion Ingreso
					OperacionIngreso operacionIngreso = RepoOperacionesIngreso.getInstance().buscarOperacionEgresoPorIdentificador(identificadorEntidadCategorizable);
					unaEntidadCategorizable = new EntidadCategorizable(operacionIngreso);
				}
				else{
				}*/
			entityManager.persist(unaEntidadCategorizable);
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

	// TODO DEBUGGEAR
	public ArrayList<EntidadCategorizable> filtrarEntidadesDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);

		String sql = "SELECT ec FROM entidades_categorizables ec JOIN entidad_categoria c ON c.nombre_categoria = " +
						unaCategoria.getNombre() + " AND c.nombre_criterio = " + unaCategoria.getCriterioDeCategorizacion().getNombre() +
						"WHERE ec.id = c.operacion_id";

		TypedQuery<EntidadCategorizable> consulta = entityManager.createQuery(sql, EntidadCategorizable.class);

		List<EntidadCategorizable> lista = consulta.getResultList();

		ArrayList<EntidadCategorizable> listaRetornable = new ArrayList<>(lista.stream().filter( entidad -> entidad.esDeLaOrganizacion(unaOrganizacion)).collect(Collectors.toList()));

		return listaRetornable;
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
