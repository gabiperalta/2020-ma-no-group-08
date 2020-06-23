import static org.junit.Assert.*;

import java.util.ArrayList;

import dominio.presupuestos.*;
import org.junit.Before;
import org.junit.Test;

import dominio.operaciones.ETipoItem;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;

public class PresupuestosTest {

	OperacionEgreso compra;
	Presupuesto presupuesto1;
	Presupuesto presupuesto2;
	Item flete;
	Item computadora;
	EntidadOperacion proveedor;
	int montoTotal;
	ArrayList<Item> items;
	ArrayList<Presupuesto> presupuestos;
	Licitacion licitacion;
	
	
	@Before
	public void init() {
		items = new ArrayList<Item>();
		presupuestos = new ArrayList<Presupuesto>();
		flete = new Item(500, ETipoItem.SERVICIO, "Servicio de transporte de productos");
		computadora = new Item(100000,ETipoItem.ARTICULO,"Notebook marca ASUS");
		items.add(flete);
		items.add(computadora);
		presupuestos.add(presupuesto1);
		presupuestos.add(presupuesto2);
		montoTotal = 2000;
		presupuesto1 = new Presupuesto(proveedor,items,montoTotal);
		licitacion = new Licitacion(compra,5);
		
	}
	

}
