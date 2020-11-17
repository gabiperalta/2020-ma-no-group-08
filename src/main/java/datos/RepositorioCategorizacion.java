package datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dominio.categorizacion.Categoria;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Empresa;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.Operacion;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import mock.ServerDataMock;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class RepositorioCategorizacion {
	
	//private ArrayList<CriterioDeCategorizacion> criteriosDeCategorizacion;
	
	//private ArrayList<EntidadCategorizable> entidadesCategorizables;

	EntityManager entityManager;

	public RepositorioCategorizacion(EntityManager em){
		entityManager = em;
	}

	/*
	private static class RepositorioCategorizacionHolder {		
        static final RepositorioCategorizacion singleInstanceRepositorioCategorizacion = new RepositorioCategorizacion();
    }
	
	public static RepositorioCategorizacion getInstance() {
        return RepositorioCategorizacionHolder.singleInstanceRepositorioCategorizacion;
    }
	 */
	
	public RepositorioCategorizacion() {
		//this.criteriosDeCategorizacion = new ArrayList<CriterioDeCategorizacion>();
		//this.entidadesCategorizables = new ArrayList<EntidadCategorizable>();
	}

	public ArrayList<CriterioDeCategorizacion> getCriteriosDeCategorizacion(){
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<CriterioDeCategorizacion> consulta = cb.createQuery(CriterioDeCategorizacion.class);
		Root<CriterioDeCategorizacion> criteriosDeCategorizacion = consulta.from(CriterioDeCategorizacion.class);
		return new ArrayList<>(this.entityManager.createQuery(consulta.select(criteriosDeCategorizacion)).getResultList());
	}
	
	// Criterios De Categorizacion
	public CriterioDeCategorizacion buscarCriterioDeCategorizacion(String nombreCriterioDeCategorizacion) {

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<CriterioDeCategorizacion> consulta = cb.createQuery(CriterioDeCategorizacion.class);
		Root<CriterioDeCategorizacion> criterios = consulta.from(CriterioDeCategorizacion.class);
		Predicate condicion = cb.equal(criterios.get("nombre"), nombreCriterioDeCategorizacion);
		CriteriaQuery<CriterioDeCategorizacion> where = consulta.select(criterios).where(condicion);

		List<CriterioDeCategorizacion> listacriterios = this.entityManager.createQuery(where).getResultList();

		if(listacriterios.size() > 0)
			return listacriterios.get(0);
		else
			return null;

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

	private EntidadCategorizable buscarEntidadEntreLasYaCategorizadas(String identificadorEntidadCategorizable) throws CategorizacionException {
		int identificadorInt;
		if(identificadorEntidadCategorizable.startsWith("OE")){
			identificadorInt = Integer.parseInt(identificadorEntidadCategorizable.substring(3));
		}
		else{
			if(identificadorEntidadCategorizable.startsWith("L")){
				identificadorInt = Integer.parseInt(identificadorEntidadCategorizable.split("-")[1]);
			}
			else{
				throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
			}
		}

		return entityManager.find(EntidadCategorizable.class, identificadorInt);
	}

	private boolean existeEntidadEntreLasCategorizadas(String identificadorEntidadCategorizable) throws CategorizacionException {
		return entityManager.contains(this.buscarEntidadEntreLasYaCategorizadas(identificadorEntidadCategorizable));
	}
	
	// ENTIDADES CATEGORIZABLES
	private EntidadCategorizable buscarEntidadCategorizable(String identificadorEntidadCategorizable) throws CategorizacionException {
		EntidadCategorizable unaEntidadCategorizable;
		if(existeEntidadEntreLasCategorizadas(identificadorEntidadCategorizable)){
			unaEntidadCategorizable = buscarEntidadEntreLasYaCategorizadas(identificadorEntidadCategorizable);
		}
		else { // TODO, VERIFICAR, CREO QUE NUNCA ENTRARIA AQUI
			if(identificadorEntidadCategorizable.startsWith("OE")) { // OE por Operacion Egreso
				RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(entityManager) ;
				unaEntidadCategorizable = repoOperacionesEgreso.buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorEntidadCategorizable);
			}
			else {
				if (identificadorEntidadCategorizable.startsWith("L")) {

					RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);
					String identificadorPresupuesto = identificadorEntidadCategorizable.split("-")[2];
					Licitacion licitacion = repoLicitaciones.buscarLicitacionPorIdentificador(identificadorEntidadCategorizable);
					unaEntidadCategorizable = licitacion.getPresupuestos().stream().filter(unPresupuesto -> unPresupuesto.getIdentificador().endsWith(identificadorPresupuesto)).
							findFirst().get();
				} else
					throw new CategorizacionException("Identificador de Entidad Categorizable INVALIDO");
			}
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

		String sql = "SELECT e FROM EntidadCategorizable e JOIN e.categoriasAsociadas ec ON ec.nombre ='" + unaCategoria.getNombre() +
				"' AND ec.criterioDeCategorizacion.nombre = '" + unaCategoria.getCriterioDeCategorizacion().getNombre() + "'";

		TypedQuery<EntidadCategorizable> consulta = entityManager.createQuery(sql, EntidadCategorizable.class);

		List<EntidadCategorizable> lista = consulta.getResultList();

		ArrayList<EntidadCategorizable> listaRetornable = new ArrayList<>(lista.stream().filter( entidad -> entidad.esDeLaOrganizacion(unaOrganizacion)).collect(Collectors.toList()));

		return listaRetornable;
	}

	public ArrayList<OperacionEgreso> filtrarEgresosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return new ArrayList<OperacionEgreso>(this.filtrarEntidadesDeLaCategoria(nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion).stream().
						filter( entidad -> entidad.getIdentificador().startsWith("OE")).map( egreso -> (OperacionEgreso)egreso ).collect(Collectors.toList()));
	}

	public ArrayList<Presupuesto> filtrarPresupuestosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		return new ArrayList<Presupuesto>(this.filtrarEntidadesDeLaCategoria(nombreCategoria, nombreCriterioDeCategorizacion, unaOrganizacion).stream().
				filter( entidad -> entidad.getIdentificador().startsWith("L")).map( presupuesto -> (Presupuesto)presupuesto ).collect(Collectors.toList()));
	}

	public List<HashMap<String,Object>> filtrarPresupuestosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion,int limite, int base){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> tupla = cb.createTupleQuery();
		Root<EntidadCategorizable> entidadCategorizableRoot = tupla.from(EntidadCategorizable.class);
		Join<Object,Object> categorias = entidadCategorizableRoot.join("categoriasAsociadas",JoinType.INNER);
		Join<Object,Object> criteriosDeCategorizacion = categorias.join("criterioDeCategorizacion",JoinType.INNER);
		Root<Licitacion> licitaciones = tupla.from(Licitacion.class);
		Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);

		tupla.select(cb.tuple(licitaciones,presupuestos)).where(
				cb.and(cb.equal(entidadCategorizableRoot.type(),cb.literal(Presupuesto.class)),
						cb.equal(categorias.get("nombre"),unaCategoria.getNombre()),
						cb.equal(criteriosDeCategorizacion.get("nombre"),unaCategoria.getCriterioDeCategorizacion().getNombre()),
						cb.equal(presupuestos.get("id"),entidadCategorizableRoot.get("id"))));

		List<Tuple> resultadoTupla = this.entityManager.createQuery(tupla).setFirstResult(base).setMaxResults(limite).getResultList();
		List<HashMap<String,Object>> resultadoHashMap = new ArrayList<>();

		for(Tuple t : resultadoTupla){
			HashMap<String,Object> presupuestoConEgresoId = new HashMap<>();
			presupuestoConEgresoId.put("presupuesto",t.get(1));
			Licitacion licitacionTupla = (Licitacion) t.get(0);
			presupuestoConEgresoId.put("id_egreso",licitacionTupla.getOperacionEgreso().getIdentificador());
			resultadoHashMap.add(presupuestoConEgresoId);
		}

		return resultadoHashMap;
	}

	public long filtrarPresupuestosDeLaCategoriaCantidad(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cantidadOperaciones = cb.createQuery(Long.class);
		Root<EntidadCategorizable> entidadCategorizableRoot = cantidadOperaciones.from(EntidadCategorizable.class);
		Join<Object,Object> categorias = entidadCategorizableRoot.join("categoriasAsociadas",JoinType.INNER);
		Join<Object,Object> criteriosDeCategorizacion = categorias.join("criterioDeCategorizacion",JoinType.INNER);

		cantidadOperaciones.select(cb.count(entidadCategorizableRoot)).where(
				cb.and(cb.equal(entidadCategorizableRoot.type(),cb.literal(Presupuesto.class)),
						cb.equal(categorias.get("nombre"),unaCategoria.getNombre()),
						cb.equal(criteriosDeCategorizacion.get("nombre"),unaCategoria.getCriterioDeCategorizacion().getNombre())));

		return this.entityManager.createQuery(cantidadOperaciones).getSingleResult();
	}

	public List<OperacionEgreso> filtrarEgresosDeLaCategoria(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion,int limite, int base){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);
		List<String> razonSocialDeEntidades = unaOrganizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<OperacionEgreso> consulta = cb.createQuery(OperacionEgreso.class);
		Root<EntidadCategorizable> entidadCategorizableRoot = consulta.from(EntidadCategorizable.class);
		Join<Object,Object> categorias = entidadCategorizableRoot.join("categoriasAsociadas",JoinType.INNER);
		Join<Object,Object> criteriosDeCategorizacion = categorias.join("criterioDeCategorizacion",JoinType.INNER);
		Root<OperacionEgreso> egresos = consulta.from(OperacionEgreso.class);
		Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

		consulta.select(egresos).where(
				cb.and(cb.equal(entidadCategorizableRoot.type(),cb.literal(OperacionEgreso.class)),
						cb.equal(categorias.get("nombre"),unaCategoria.getNombre()),
						cb.equal(criteriosDeCategorizacion.get("nombre"),unaCategoria.getCriterioDeCategorizacion().getNombre()),
						cb.equal(egresos.get("id"),entidadCategorizableRoot.get("id")),
						entidades.get("nombre").in(razonSocialDeEntidades)));

		return this.entityManager.createQuery(consulta).setFirstResult(base).setMaxResults(limite).getResultList();
	}

	public long filtrarEgresosDeLaCategoriaCantidad(String nombreCategoria, String nombreCriterioDeCategorizacion, Organizacion unaOrganizacion){
		Categoria unaCategoria = this.buscarCriterioDeCategorizacion(nombreCriterioDeCategorizacion).buscarCategoria(nombreCategoria);
		List<String> razonSocialDeEntidades = unaOrganizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cantidadOperaciones = cb.createQuery(Long.class);
		Root<EntidadCategorizable> entidadCategorizableRoot = cantidadOperaciones.from(EntidadCategorizable.class);
		Join<Object,Object> categorias = entidadCategorizableRoot.join("categoriasAsociadas",JoinType.INNER);
		Join<Object,Object> criteriosDeCategorizacion = categorias.join("criterioDeCategorizacion",JoinType.INNER);
		Root<OperacionEgreso> egresos = cantidadOperaciones.from(OperacionEgreso.class);
		Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

		cantidadOperaciones.select(cb.count(entidadCategorizableRoot)).where(
				cb.and(cb.equal(entidadCategorizableRoot.type(),cb.literal(OperacionEgreso.class)),
						cb.equal(categorias.get("nombre"),unaCategoria.getNombre()),
						cb.equal(criteriosDeCategorizacion.get("nombre"),unaCategoria.getCriterioDeCategorizacion().getNombre()),
						cb.equal(egresos.get("id"),entidadCategorizableRoot.get("id")),
						entidades.get("nombre").in(razonSocialDeEntidades)));

		return this.entityManager.createQuery(cantidadOperaciones).getSingleResult();
	}
}
