package datos;

import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class RepoOrganizaciones {
    private static ArrayList<Organizacion> organizaciones;
    private EntityManager entityManager;

    private static class RepositorioOrganizacionesHolder {
        static final RepoOrganizaciones singleInstanceRepositorioOrganizaciones = new RepoOrganizaciones();
    }

    public static RepoOrganizaciones getInstance() {
        return RepoOrganizaciones.RepositorioOrganizacionesHolder.singleInstanceRepositorioOrganizaciones;
    }

    public RepoOrganizaciones() {} //TODO: eliminar
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

    public void agregarOrganizacion(String nombreOrganizacion, ArrayList<EntidadJuridica> entidades) {
        if (!this.existeLaOrganizacion(nombreOrganizacion)) {
            Organizacion organizacion = new Organizacion();
            organizacion.setNombre(nombreOrganizacion);
            organizacion.setEntidades(entidades);
            entityManager.persist(organizacion);
        }
    }

    public void eliminarOrganizacion(String nombreOrganizacion) {
        Organizacion organizacionABorrar = buscarOrganizacion(nombreOrganizacion);
        if (organizacionABorrar != null) {
            organizaciones.remove(organizacionABorrar);
        }
    }

    public boolean existeLaOrganizacion(String nombreOrganizacion) {
        boolean existeLaOrganizacion;
        try {
            this.buscarOrganizacion(nombreOrganizacion);
            existeLaOrganizacion = true;
        }
        catch (Exception NoSuchElementException){
            existeLaOrganizacion = false;
        }
        return existeLaOrganizacion;
    }

    public static Organizacion buscarOrganizacion(String nombreOrganizacion) {
        return organizaciones.stream().filter(org -> org.getNombre().equals(nombreOrganizacion)).findFirst().get();
    }
}
