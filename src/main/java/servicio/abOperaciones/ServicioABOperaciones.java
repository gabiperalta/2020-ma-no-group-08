package servicio.abOperaciones;

import dominio.operaciones.*;

import java.util.ArrayList;
import java.util.Date;

public class ServicioABOperaciones {

    public void altaOperacion(ArrayList<Item> items, MedioDePago medioDePago, DocumentoComercial documento, Date fecha, double valorOperacion, EntidadOperacion entidadOrigen, EntidadOperacion entidadDestino){
        OperacionEgresoBuilder operacionEgresoBuilder = new OperacionEgresoBuilder();
        operacionEgresoBuilder.setItems(items);
        operacionEgresoBuilder.setMedioDePago(medioDePago);
        operacionEgresoBuilder.setDocumento(documento);
        operacionEgresoBuilder.setFecha(fecha);
        operacionEgresoBuilder.setValorOperacion(valorOperacion);
        operacionEgresoBuilder.setEntidadOrigen(entidadOrigen);
        operacionEgresoBuilder.setEntidadDestino(entidadDestino);

        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(operacionEgresoBuilder.build());
    }

    public void bajaOperacion(OperacionEgreso operacionEgreso){
        RepoOperacionesEgreso.getInstance().eliminarOperacionEgreso(operacionEgreso);
    }

    public ArrayList<OperacionEgreso> listarOperaciones(){
        return RepoOperacionesEgreso.getInstance().getOperacionesEgreso();
    }
}
