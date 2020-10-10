package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import java.util.ArrayList;

public class RepoOrganizaciones {
    private static ArrayList<Organizacion> organizaciones;


    private static class RepositorioOrganizacionesHolder {
        static final RepoOrganizaciones singleInstanceRepositorioOrganizaciones = new RepoOrganizaciones();
    }

    public static RepoOrganizaciones getInstance() {
        return RepoOrganizaciones.RepositorioOrganizacionesHolder.singleInstanceRepositorioOrganizaciones;
    }

    public RepoOrganizaciones() {

        Organizacion organizacion1 = new Organizacion("org1", null);
        Organizacion organizacion2 = new Organizacion("org2",null);
        Organizacion organizacion3 = new Organizacion("org3",null);

        organizaciones = new ArrayList<Organizacion>();

        organizaciones.add(organizacion1);
        organizaciones.add(organizacion2);
        organizaciones.add(organizacion3);
    }

    public void agregarOrganizacion(String nombreOrganizacion, ArrayList<EntidadJuridica> entidades) {
        if (!this.existeLaOrganizacion(nombreOrganizacion)) {
            Organizacion organizacion = new Organizacion(nombreOrganizacion,entidades);
            organizaciones.add(organizacion);
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
