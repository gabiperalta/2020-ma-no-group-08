import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dominio.operaciones.CriterioCantidadPresupCargada;
import dominio.operaciones.ETipoItem;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;
import dominio.presupuestos.CriterioCantidadDePresupuestos;
import dominio.presupuestos.CriterioLicitacion;
import dominio.presupuestos.CriterioMenorPrecio;
import dominio.presupuestos.Licitacion;
import dominio.presupuestos.Presupuesto;

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
	ArrayList<CriterioLicitacion> criterios;
	CriterioCantidadDePresupuestos criterio1;
	CriterioCantidadPresupCargada criterio2;
	CriterioMenorPrecio criterio3;
	Licitacion licitacion;
	
	
	@Before
	public void init() {
		items = new ArrayList<Item>();
		presupuestos = new ArrayList<Presupuesto>();
		criterios = new ArrayList<CriterioLicitacion>();
		flete = new Item(500, ETipoItem.SERVICIO, "Servicio de transporte de productos");
		computadora = new Item(100000,ETipoItem.ARTICULO,"Notebook marca ASUS");
		criterio1 = new CriterioCantidadDePresupuestos();
		criterio2 = new CriterioCantidadPresupCargada();
		criterio3 = new CriterioMenorPrecio();
		items.add(flete);
		items.add(computadora);
		presupuestos.add(presupuesto1);
		presupuestos.add(presupuesto2);
		criterios.add(criterio1);
		criterios.add(criterio2);
		criterios.add(criterio3);
		montoTotal = 2000;
		presupuesto1 = new Presupuesto(proveedor,items,montoTotal);
		licitacion = new Licitacion(compra,presupuestos,criterios);
		
	}
	

}
