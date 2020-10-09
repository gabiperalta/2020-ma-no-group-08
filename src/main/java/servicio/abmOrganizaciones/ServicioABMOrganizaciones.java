package servicio.abmOrganizaciones;

import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;
import dominio.entidades.RepoOrganizaciones;

import java.util.ArrayList;

public class ServicioABMOrganizaciones {

    public void altaOrganizacion (String nombreOrganizacion, ArrayList<EntidadJuridica> entidades) {
        if (this.buscarOrganizacion(nombreOrganizacion) == null) {
            RepoOrganizaciones.getInstance().agregarOrganizacion(nombreOrganizacion, entidades);
        }
    }

    public void bajaOrganizacion (String nombreOrganizacion){
        if (this.buscarOrganizacion(nombreOrganizacion) != null) {
            RepoOrganizaciones.getInstance().eliminarOrganizacion(nombreOrganizacion);
        }
    }

    public Organizacion buscarOrganizacion (String nombreOrganizacion) {
        return RepoOrganizaciones.getInstance().buscarOrganizacion(nombreOrganizacion);
    }
}
