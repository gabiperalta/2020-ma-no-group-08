package servicio.abOperaciones;

import dominio.operaciones.*;
import dominio.operaciones.medioDePago.MedioDePago;

import java.util.ArrayList;
import java.util.Date;

public class ServicioABOperaciones {

    public void altaOperacion(ArrayList<Item> items, MedioDePago medioDePago, DocumentoComercial documento, Date fecha, double valorOperacion, EntidadOperacion entidadOrigen, EntidadOperacion entidadDestino){
        OperacionEgresoBuilder operacionEgresoBuilder = new OperacionEgresoBuilder();
        operacionEgresoBuilder.agregarItems(items)
                            .agregarMedioDePago(medioDePago)
                            .agregarDocComercial(documento)
                            .agregarFecha(fecha)
                            .agregarValorOperacion(valorOperacion)
                            .agregarEntidadOrigen(entidadOrigen)
                            .agregarEntidadDestino(entidadDestino);

        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(operacionEgresoBuilder.build());
    }

    public void bajaOperacion(OperacionEgreso operacionEgreso){
        RepoOperacionesEgreso.getInstance().eliminarOperacionEgreso(operacionEgreso);
    }

    public ArrayList<OperacionEgreso> listarOperaciones(){
        return RepoOperacionesEgreso.getInstance().getOperacionesEgreso();
    }
}
