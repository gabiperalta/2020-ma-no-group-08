package servicio.abm_entidades;

import dominio.entidades.*;


public class ServicioABMEntidadesJuridicas {

    public void crearEmpresa(Integer cantidadPersonal, ETipoEmpresa tipo, String actividad, Double promedioVentas, String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            Empresa empresa = new Empresa(tipo, cantidadPersonal,  actividad,  promedioVentas,  razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
            RepoEntidadesJuridicas.getInstance().agregarEntidadEmpresa( empresa );
        }

    }

    public void crearOSC( String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            OSC osc = new OSC(razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
            RepoEntidadesJuridicas.getInstance().agregarEntidadOSC(osc);
        }

    }


    public void eliminarEntidadJuridica(String razonSocial) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            RepoEntidadesJuridicas.getInstance().eliminarEntidadJuridica(razonSocial);
        }
    }


    public Object buscarEntidadJuridica (String razonSocial) {
        return RepoEntidadesJuridicas.getInstance().buscarEntidadJuridica(razonSocial);
    }


    public void  listarEntidadJuridica(String organizacion) throws Exception {
        Organizacion org = RepoOrganizaciones.buscarOrganizacion(organizacion);
        org.getEntidades();
    }

}

