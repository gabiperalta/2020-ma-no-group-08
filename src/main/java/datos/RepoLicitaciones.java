package datos;

import dominio.entidades.Empresa;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.operaciones.Operacion;
import dominio.operaciones.OperacionEgreso;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RepoLicitaciones {

    private EntityManager entityManager;

    public RepoLicitaciones(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void agregarLicitacion(Licitacion licitacion){
        entityManager.persist(licitacion);
        /*
        String identificador = "L-";
        licitacion.setIdentificador(identificador + ultimoIdentificador);
        ultimoIdentificador++;
        licitaciones.add(licitacion);
        */
    }

    public Licitacion buscarLicitacionPorOperacionEgreso(String identificadorOperacionEgreso){
        int idEgreso;
        if(identificadorOperacionEgreso.startsWith("OE-")) {
            idEgreso = Integer.parseInt(identificadorOperacionEgreso.split("-")[1]);
        }
        else{
            idEgreso = Integer.parseInt(identificadorOperacionEgreso);
        }

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Predicate condicion = cb.equal(licitaciones.get("compra"), idEgreso);
        CriteriaQuery<Licitacion> where = consulta.select(licitaciones).where(condicion);

        List<Licitacion> listaLicitaciones = this.entityManager.createQuery(where).getResultList();

        if(listaLicitaciones.size() > 0)
            return listaLicitaciones.get(0);
        else
            return null;

        //if(licitaciones.stream().anyMatch(licitacion -> licitacion.getOperacionEgreso().getIdentificador().equals(identificadorOperacionEgreso)))
        //    return licitaciones.stream().filter(licitacion -> licitacion.getOperacionEgreso().getIdentificador().equals(identificadorOperacionEgreso)).findFirst().get();
        //else
        //    return null;
    }

    public Licitacion buscarLicitacionPorIdentificador(String identificadorLicitacion){
        int idLicitacion;
        if(identificadorLicitacion.startsWith("L-"))
            idLicitacion = Integer.parseInt(identificadorLicitacion.split("-")[1]);
        else
            idLicitacion = Integer.parseInt(identificadorLicitacion);

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Predicate condicion = cb.equal(licitaciones.get("identificadorLicitacion"), idLicitacion);
        CriteriaQuery<Licitacion> where = consulta.select(licitaciones).where(condicion);

        List<Licitacion> listaLicitaciones = this.entityManager.createQuery(where).getResultList();

        if(listaLicitaciones.size() > 0)
            return listaLicitaciones.get(0);
        else
            return null;
    }

    public ArrayList<Licitacion> getLicitaciones(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        return (ArrayList<Licitacion>) this.entityManager.createQuery(consulta.select(licitaciones).orderBy(cb.asc(licitaciones.get("identificadorLicitacion")))).getResultList();
    }

    public ArrayList<Licitacion> getLicitaciones(int limite, int base){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        return (ArrayList<Licitacion>) this.entityManager.createQuery(consulta.select(licitaciones).orderBy(cb.asc(licitaciones.get("identificadorLicitacion"))))
                .setFirstResult(base)
                .setMaxResults(limite)
                .getResultList();
    }

    public List<HashMap<String, Object>> getPresupuestosPorOrg(Organizacion organizacion, int limite, int base){
        List<String> razonSocialDeEntidades = organizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> tupla = cb.createTupleQuery();
        Root<Licitacion> licitaciones = tupla.from(Licitacion.class);
        Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);
        Join<Object,Object> egresos = licitaciones.join("compra",JoinType.INNER);
        Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

        tupla.select(cb.tuple(licitaciones,presupuestos)).where(entidades.get("nombre").in(razonSocialDeEntidades));

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

    public long getCantidadPresupuestosPorOrg(Organizacion organizacion){
        List<String> razonSocialDeEntidades = organizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);
        Join<Object,Object> egresos = licitaciones.join("compra",JoinType.INNER);
        Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

        consulta.select(cb.count(presupuestos)).where(entidades.get("nombre").in(razonSocialDeEntidades));

        return this.entityManager.createQuery(consulta).getSingleResult();
    }

    public long getCantidadPresupuestos(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);
        consulta.select(cb.count(presupuestos));
        return this.entityManager.createQuery(consulta).getSingleResult();
    }

    public List<HashMap<String,Object>> getPresupuestosConEgresoId(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        /*
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);
        consulta.select(cb.count(presupuestos));
        return this.entityManager.createQuery(consulta).getSingleResult();
        */

        CriteriaQuery<Tuple> tupla = cb.createTupleQuery();
        Root<Licitacion> licitaciones = tupla.from(Licitacion.class);
        Join<Object,Object> presupuestos = licitaciones.join("presupuestos",JoinType.INNER);
        tupla.select(cb.tuple(licitaciones,presupuestos));

        List<Tuple> resultadoTupla = this.entityManager.createQuery(tupla).getResultList();
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
}

