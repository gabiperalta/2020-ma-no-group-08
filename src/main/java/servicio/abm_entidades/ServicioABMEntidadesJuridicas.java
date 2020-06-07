package servicio.abm_entidades;

import dominio.entidades.ETipoEmpresa;
import dominio.entidades.Organizacion;


//TODO

public class ServicioABMEntidadesJuridicas {

    public void crearEmpresa(Organizacion organizacion, Integer cantidadPersonal, ETipoEmpresa tipo, EActividad actividad, Double promedioVentas, String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {


    }

    public void crearOSC(Organizacion organizacion, String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {


    }


    public void eliminarEntidadJuridica(Object empresa) throws Exception {

        if (this.buscarOrganizacion(nombreOrganizacion) == null) {
            RepoOrganizaciones.getInstance().eliminarOrganizacion(nombreOrganizacion);
        }
    }

    public void editarEntidadJuridica(Object empresa) throws Exception {


    }


    public void listarEntidadJuridica(Organizacion organizacion) throws Exception {

        organizacion.getEntidades();
    }

}

