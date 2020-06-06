package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import java.util.ArrayList;

public class RepoOrganizaciones {
    private ArrayList<Organizacion> organizaciones;


    private static class RepositorioOrganizacionesHolder {
        static final RepoOrganizaciones singleInstanceRepositorioOrganizaciones = new RepoOrganizaciones();
    }

    public static RepoOrganizaciones getInstance() {
        return RepoOrganizaciones.RepositorioOrganizacionesHolder.singleInstanceRepositorioOrganizaciones;
    }

    public RepoOrganizaciones() {

        Organizacion organizacion1 = new Organizacion("org1");
        Organizacion organizacion2 = new Organizacion("org2");
        Organizacion organizacion3 = new Organizacion("org3");

        organizaciones = new ArrayList<Organizacion>();

        organizaciones.add(organizacion1);
        organizaciones.add(organizacion2);
        organizaciones.add(organizacion3);
    }

    public void agregarOrganizacion(Organizacion organizacion) {
        organizaciones.add(organizacion);
    }

    public void eliminarOrganizacion(String nombreOrganizacion) {
        Organizacion organizacionABorrar = this.buscarOrganizacion(nombreOrganizacion);
        if (organizacionABorrar != null) {
            organizaciones.remove(organizacionABorrar);
        }
    }

    public Organizacion buscarOrganizacion(String nombreOrganizacion) {
        return organizaciones.stream().filter(org -> org.getNombre().equals(nombreOrganizacion)).findFirst().get();
    }
}
