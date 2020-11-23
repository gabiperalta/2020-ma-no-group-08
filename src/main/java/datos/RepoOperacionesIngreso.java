package datos;

import dominio.entidades.Organizacion;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class RepoOperacionesIngreso {
    private EntityManager entityManager;

    public RepoOperacionesIngreso(EntityManager em){
        entityManager = em;
    }

    public void agregarIngreso(OperacionIngreso ingreso) throws Exception {
        if (!existeEgreso(ingreso)) {
            entityManager.persist(ingreso);
        }
    }

    public ArrayList<OperacionIngreso> getIngresos() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OperacionIngreso> consulta = cb.createQuery(OperacionIngreso.class);
        Root<OperacionIngreso> operacionesIngresoC = consulta.from(OperacionIngreso.class);
        return new ArrayList<>(this.entityManager.createQuery(consulta.select(operacionesIngresoC)).getResultList());
    }

    private boolean existeEgreso(OperacionIngreso operacionIngreso) {
        return entityManager.contains(operacionIngreso);
    }

    public void eliminarIngreso(OperacionIngreso ingreso) {
        entityManager.remove(ingreso);
    }

    public OperacionIngreso buscarOperacionIngresoPorIdentificador(String identificadorIngreso) {
        String identificadorAcotado = identificadorIngreso.substring(3);
        int identificador = Integer.parseInt(identificadorAcotado);
        return entityManager.find(OperacionIngreso.class, identificador);
    }
}