package dominio.presupuestos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dominio.excepciones.LaCompraNoRequierePresupuestosException;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;


public class LicitacionTest {
	OperacionEgreso compra; 
	Licitacion licitacion;
	Presupuesto presup1;
	Presupuesto presup2;
	ArrayList<Presupuesto> presupuestos;
	Item item1;
	Item item2;
	Item item3;
	Item item4;
	ArrayList<Item> listaItems1;
	ArrayList<Item> listaItems2;
	EntidadOperacion proveedor;
	
	@Before
	public void init() {
		compra = new OperacionEgreso();
		presupuestos = new ArrayList<Presupuesto>();
		licitacion = new Licitacion(compra,50);
		licitacion.setPresupuestos(presupuestos);
		item1 = new Item();
		item1.setValor(50);
		item2 = new Item();
		item2.setValor(100);
		item3 = new Item();
		item3.setValor(200);
		item4 = new Item();
		item4.setValor(150);
		listaItems1 = new ArrayList<Item>();
		listaItems1.add(item1);
		listaItems1.add(item2);
		listaItems2 = new ArrayList<Item>();
		listaItems2.add(item3);
		listaItems2.add(item4);
		proveedor = new EntidadOperacion();
		presup1 = new Presupuesto(proveedor,listaItems1,150.0);
		presup2 = new Presupuesto(proveedor,listaItems2,350.0);
	}
	
	@Test
	public void agregarPresupuestoValido() {
		compra.setItems(listaItems1);
		licitacion.agregarPresupuesto(presup1);
		assertTrue(licitacion.getPresupuestos().contains(presup1));
	}
	
	@Test
	public void agregarPresupuestoInvalido() {
		compra.setItems(listaItems2);
		licitacion.agregarPresupuesto(presup1);
		assertFalse(licitacion.getPresupuestos().contains(presup1));
	}
	
    @Test
    public void licitarExitoso() {
    	licitacion.setPresupuestosNecesarios(5);
		compra.setPresupuestosNecesarios(5);
		presupuestos.add(presup1);
		presupuestos.add(presup2);
		assertTrue(licitacion.puedeLicitar());
    }
    
    @Test
    public void licitarNoExitosoPorqueNoCumpleCriterio1() {
		compra.setPresupuestosNecesarios(5);
		licitacion.setPresupuestosNecesarios(10);
		presupuestos.add(presup2);
		presupuestos.add(presup1);
		assertFalse(licitacion.puedeLicitar());
    }
    
    @Test(expected = LaCompraNoRequierePresupuestosException.class)
    public void licitarNoExitosoPorqueNoCumpleCriterio2() {
    	compra.setPresupuestosNecesarios(0);
    	assertFalse(licitacion.puedeLicitar());
    }
    
    @Test
    public void licitarNoExitosoPorqueNoCumpleCriterio3() {
    	licitacion.setPresupuestosNecesarios(5);
		compra.setPresupuestosNecesarios(5);
    	presupuestos.add(presup2);
		presupuestos.add(presup1);
		assertFalse(licitacion.puedeLicitar());
    }
}