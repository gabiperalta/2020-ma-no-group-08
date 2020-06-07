package servicio.abm_entidades;

import dominio.entidades.*;


//TODO

public class ServicioABMEntidadesJuridicas {

    public void crearEmpresa(Integer cantidadPersonal, ETipoEmpresa tipo, String actividad, Double promedioVentas, String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            RepoEntidadesJuridicas.getInstance().agregarEntidadEmpresa( tipo, cantidadPersonal,  actividad,  promedioVentas,  razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
        }

    }

    public void crearOSC( String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            RepoEntidadesJuridicas.getInstance().agregarEntidadOSC(razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
        }

    }


    public void eliminarEntidadJuridica(String razonSocial) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            RepoEntidadesJuridicas.getInstance().eliminarEntidadJuridica(razonSocial);
        }
    }

    //TODO: como se que dato modificar?
    public void editarEntidadJuridica(Object empresa) throws Exception {


    }

    public Object buscarEntidadJuridica (String razonSocial) {
        return RepoEntidadesJuridicas.getInstance().buscarEntidadJuridica(razonSocial);
    }


    public void  listarEntidadJuridica(String organizacion) throws Exception {
        Organizacion org = RepoOrganizaciones.buscarOrganizacion(organizacion);
        org.getEntidades();
    }

}

