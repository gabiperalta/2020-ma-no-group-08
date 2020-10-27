package datos;

import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;

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
        return entityManager.find(Organizacion.class, nombreOrganizacion);
    }
}
