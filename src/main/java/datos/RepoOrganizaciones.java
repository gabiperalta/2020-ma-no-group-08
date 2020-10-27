package datos;

import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class RepoOrganizaciones {
    //private static ArrayList<Organizacion> organizaciones;
    private EntityManager entityManager;

//    private static class RepositorioOrganizacionesHolder {
//        static final RepoOrganizaciones singleInstanceRepositorioOrganizaciones = new RepoOrganizaciones();
//    }
//
//    public static RepoOrganizaciones getInstance() {
//        return RepoOrganizaciones.RepositorioOrganizacionesHolder.singleInstanceRepositorioOrganizaciones;
//    }

    //public RepoOrganizaciones() {} //TODO: eliminar
    public RepoOrganizaciones(EntityManager em) {
        entityManager = em;

//TODO: revisar
//        Organizacion organizacion1 = new Organizacion("org1", null);
//        Organizacion organizacion2 = new Organizacion("org2",null);
//        Organizacion organizacion3 = new Organizacion("org3",null);
//
//        organizaciones = new ArrayList<Organizacion>();
//
//        organizaciones.add(organizacion1);
//        organizaciones.add(organizacion2);
//        organizaciones.add(organizacion3);
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
