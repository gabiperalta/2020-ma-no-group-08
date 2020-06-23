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
    OperacionEgresoBuilder builder;

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
        builder = new OperacionEgresoBuilder();
    }

    @Test
    public void crearOperacionValida () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNotNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinItems () {
        builder.agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinMedioDePago () {
        builder.agregarItems(items)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinDocComercial () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinFecha () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinValorDeOperacion () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinEntidadDeOrigen () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadDestino(destino);
        assertNull(builder.build());
    }

    @Test
    public void fallaCrearOperacionInValidaSinEntidadDeDestino () {
        builder.agregarItems(items)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarValorOperacion(valorOp)
                .agregarEntidadOrigen(origen);
        assertNull(builder.build());
    }
}