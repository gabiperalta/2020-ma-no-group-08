package servicio.abmOrganizaciones;

import dominio.entidades.Organizacion;
import dominio.entidades.RepoOrganizaciones;

public class ServicioABMOrganizaciones {

    public void altaOrganizacion (String nombreOrganizacion) {
        if (this.buscarOrganizacion(nombreOrganizacion) == null) {
            RepoOrganizaciones.getInstance().agregarOrganizacion(nombreOrganizacion);
        }
    }

    public void bajaOrganizacion (String nombreOrganizacion){
        if (this.buscarOrganizacion(nombreOrganizacion) == null) {
            RepoOrganizaciones.getInstance().eliminarOrganizacion(nombreOrganizacion);
        }
    }

    public Organizacion buscarOrganizacion (String nombreOrganizacion) {
        return RepoOrganizaciones.getInstance().buscarOrganizacion(nombreOrganizacion);
    }


}
