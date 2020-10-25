package datos;

import dominio.licitacion.Licitacion;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Predicate condicion = cb.equal(licitaciones.get("egreso_id"), identificadorOperacionEgreso); // deberia ser un int?
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
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        Predicate condicion = cb.equal(licitaciones.get("id"), identificadorLicitacion); // deberia ser un int?
        CriteriaQuery<Licitacion> where = consulta.select(licitaciones).where(condicion);

        List<Licitacion> listaLicitaciones = this.entityManager.createQuery(where).getResultList();

        if(listaLicitaciones.size() > 0)
            return listaLicitaciones.get(0);
        else
            return null;

        //if(licitaciones.stream().anyMatch(licitacion -> licitacion.getIdentificador().equals(identificadorLicitacion)))
        //    return licitaciones.stream().filter(licitacion -> licitacion.getIdentificador().equals(identificadorLicitacion)).findFirst().get();
        //else
        //    return null;
    }

    public ArrayList<Licitacion> getLicitaciones(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        return (ArrayList<Licitacion>) this.entityManager.createQuery(consulta.select(licitaciones)).getResultList();
    }
}

