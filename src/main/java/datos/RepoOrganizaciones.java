package datos;

import dominio.entidades.Empresa;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;
import java.util.List;



import javax.persistence.EntityManager;
import java.util.ArrayList;

public class RepoOrganizaciones {
    private EntityManager entityManager;

    public RepoOrganizaciones(EntityManager em) {
        entityManager = em;
    }

    public void agregarOrganizacion(String nombreOrganizacion, ArrayList<Empresa> entidades) {
        if (!this.existeLaOrganizacion(nombreOrganizacion)) {
            Organizacion organizacion = new Organizacion(nombreOrganizacion, entidades);
            entityManager.persist(organizacion);
        }
    }

    public void eliminarOrganizacion(String nombreOrganizacion) {
        if (this.existeLaOrganizacion(nombreOrganizacion)) {
            entityManager.remove(this.buscarOrganizacion(nombreOrganizacion));
        }
    }

    public boolean existeLaOrganizacion(String nombreOrganizacion) {
        return entityManager.contains(this.buscarOrganizacion(nombreOrganizacion));
    }

    public Organizacion buscarOrganizacion(String nombreOrganizacion) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Organizacion> consulta = cb.createQuery(Organizacion.class);
        Root<Organizacion> organizaciones = consulta.from(Organizacion.class);
        Predicate condicion = cb.equal(organizaciones.get("nombre"), nombreOrganizacion);
        CriteriaQuery<Organizacion> where = consulta.select(organizaciones).where(condicion);

        List<Organizacion> listaOrganizaciones = this.entityManager.createQuery(where).getResultList();

        if(listaOrganizaciones.size() > 0)
            return listaOrganizaciones.get(0);
        else
            return null;
    }
}
