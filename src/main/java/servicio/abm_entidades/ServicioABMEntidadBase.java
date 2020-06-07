package servicio.abm_entidades;

import dominio.entidades.*;


//TODO

public class ServicioABMEntidadesBase {


    public void crearEntidadBase( String descripcion, String nombreFicticio, Object entidadJuridica ) throws Exception {

        if (this.buscarEntidadBase(nombreFicticio) == null) {
            RepoEntidadesBase.getInstance().agregarEntidadBase( nombreFicticio,  descripcion,  entidadJuridica);
        }
    }


    public void eliminarEntidadBase(String nombreFicticio) throws Exception {

        if (this.buscarEntidadBase(razonSocial) == null) {
            RepoEntidadesBase.getInstance().eliminarEntidadBase(nombreFicticio);
        }
    }

    //TODO: como se que dato modificar?
    public void editarEntidadBase() throws Exception {


    }

    public Object buscarEntidadBase (String nombreFicticio) {
        return RepoEntidadesBase.getInstance().buscarEntidadBase(nombreFicticio);
    }


}

