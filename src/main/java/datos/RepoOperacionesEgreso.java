package datos;

import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.entidades.Empresa;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.OperacionEgreso;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RepoOperacionesEgreso {
    private EntityManager entityManager;

    public RepoOperacionesEgreso(EntityManager em){
        entityManager = em;
    }

    public void agregarOperacionEgreso(OperacionEgreso operacionEgreso) throws Exception {
        if (!existeEgreso(operacionEgreso)) {
            cargarEntidadOperacion(operacionEgreso.getEntidadOrigen());
            cargarEntidadOperacion(operacionEgreso.getEntidadDestino());
            entityManager.persist(operacionEgreso);
        }
    }

    private void cargarEntidadOperacion(EntidadOperacion entidadOperacion) {
        if (!entityManager.contains(entidadOperacion)) {
            entityManager.persist(entidadOperacion);
        }
    }

    private boolean existeEgreso(OperacionEgreso operacionEgreso) {
        return entityManager.contains(operacionEgreso);
    }

    public void eliminarOperacionEgreso(OperacionEgreso operacionEgreso) {
        entityManager.remove(operacionEgreso);
    }

    public void eliminarOperacionEgresoPorIdentificador(String identificadorOperacionEgreso) {
        OperacionEgreso egresoAEliminar = buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorOperacionEgreso);
        entityManager.remove(egresoAEliminar);
    }

    public OperacionEgreso buscarOperacionEgresoPorIdenticadorOperacionEgreso(String identifacdorOperacionEgreso){
        String identificadorAcotado = identifacdorOperacionEgreso.substring(3);
        int identificador = Integer.parseInt(identificadorAcotado);
        return entityManager.find(OperacionEgreso.class, identificador);
    }

    public ArrayList<OperacionEgreso> getOperacionesEgreso(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OperacionEgreso> consulta = cb.createQuery(OperacionEgreso.class);
        Root<OperacionEgreso> operacionesEgresoC = consulta.from(OperacionEgreso.class);
        return new ArrayList<>(this.entityManager.createQuery(consulta.select(operacionesEgresoC)).getResultList());
    }

    public List<OperacionEgreso> getOperacionesEgresoPorOrg(Organizacion organizacion, int limite, int base){
        List<String> razonSocialDeEntidades = organizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OperacionEgreso> consulta = cb.createQuery(OperacionEgreso.class);
        Root<OperacionEgreso> egresos = consulta.from(OperacionEgreso.class);
        Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

        consulta.select(egresos).where(entidades.get("nombre").in(razonSocialDeEntidades));

        return this.entityManager.createQuery(consulta).setFirstResult(base).setMaxResults(limite).getResultList();
    }

    public long getCantidadOperacionesEgresoPorOrg(Organizacion organizacion){
        List<String> razonSocialDeEntidades = organizacion.getEntidades().stream().map(Empresa::getRazonSocial).collect(Collectors.toList());

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<OperacionEgreso> egresos = consulta.from(OperacionEgreso.class);
        Join<Object,Object> entidades = egresos.join("entidadOrigen",JoinType.INNER);

        consulta.select(cb.count(egresos)).where(entidades.get("nombre").in(razonSocialDeEntidades));

        return this.entityManager.createQuery(consulta).getSingleResult();
    }
}

