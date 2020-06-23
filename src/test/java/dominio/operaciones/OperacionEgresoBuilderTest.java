package dominio.operaciones;

import dominio.operaciones.medioDePago.Efectivo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class OperacionEgresoBuilderTest {
    Item computadora;
    Item flete;
    Efectivo pesos;
    DocumentoComercial documento;
    Date fecha;
    Double valorOp;
    EntidadOperacion origen;
    EntidadOperacion destino;
    ArrayList<Item> items;
    OperacionEgresoBuilder target;

    @Before
    public void init() {
        items = new ArrayList<Item>();
        computadora = new Item(100000,ETipoItem.ARTICULO,"Notebook marca ASUS");
        flete = new Item(500, ETipoItem.SERVICIO, "Servicio de transporte de productos");
        items.add(flete);
        items.add(computadora);
        pesos = new Efectivo(200000,"Rapipago");
        documento = new DocumentoComercial(ETipoDoc.FACTURA, 2000);
        fecha = new Date();
        valorOp = 100500d;
        origen = new EntidadOperacion("Operacion compra 1","20-40678950-4","Av.Libertador 800");
        destino = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Corrientes 550");
        target = new OperacionEgresoBuilder();
    }

    @Test
    public void crearOperacionValida () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNotNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinItems () {
        target.agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinMedioDePago () {
        target.agregarItems(items)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinDocComercial () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinFecha () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinValorDeOperacion () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinEntidadDeOrigen () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadDestino(destino);
        assertNull(target.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinEntidadDeDestino () {
        target.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen);
        assertNull(target.build());
    }
}