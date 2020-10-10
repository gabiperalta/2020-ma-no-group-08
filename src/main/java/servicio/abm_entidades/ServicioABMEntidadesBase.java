package servicio.abm_entidades;

import datos.RepoEntidadesBase;
import dominio.entidades.*;


//TODO

public class ServicioABMEntidadesBase {


    //TODO ver aca el tema de object porque puede ser empresa o osc y no se puede poner tipo entitadjuridica

    public void crearEntidadBase( String descripcion, String nombreFicticio, EntidadJuridica entidadJuridica ) throws Exception {

        if (this.buscarEntidadBase(nombreFicticio) == null) {
            RepoEntidadesBase.getInstance().agregarEntidadBase( nombreFicticio,  descripcion, entidadJuridica);
        }
    }


    public void eliminarEntidadBase(String nombreFicticio) throws Exception {

        if (this.buscarEntidadBase(nombreFicticio) == null) {
            RepoEntidadesBase.getInstance().eliminarEntidadBase(nombreFicticio);
        }
    }


    public Object buscarEntidadBase (String nombreFicticio) {
        return RepoEntidadesBase.getInstance().buscarEntidadBase(nombreFicticio);
    }


}

