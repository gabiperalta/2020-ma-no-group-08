package servicio.abOperaciones;

import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.MedioDePago;

import java.util.ArrayList;
import java.util.Date;

public class ServicioABOperaciones {

    public void altaOperacion(ArrayList<Item> items, MedioDePago medioDePago, DocumentoComercial documento, Date fecha, double valorOperacion, EntidadOperacion entidadOrigen, EntidadOperacion entidadDestino) throws Exception{
        OperacionEgresoBuilder operacionEgresoBuilder = new OperacionEgresoBuilder();
        operacionEgresoBuilder.agregarItems(items)
                            .agregarMedioDePago(medioDePago)
                            .agregarDocComercial(documento)
                            .agregarFecha(fecha)
                            .agregarEntidadOrigen(entidadOrigen)
                            .agregarEntidadDestino(entidadDestino);

        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(operacionEgresoBuilder.build());
    }

    public void bajaOperacion(OperacionEgreso operacionEgreso){
        RepoOperacionesEgreso.getInstance().eliminarOperacionEgreso(operacionEgreso);
    }

    public ArrayList<OperacionEgreso> listarOperaciones(Organizacion org){
        return RepoOperacionesEgreso.getInstance().getOperacionesEgreso();
    }
}
