package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;

public class Presupuesto {
	private EntidadOperacion proveedor;
	private ArrayList<Item> items;
	private int precioTotal;
	
	
	public int getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(int precioTotal) {
		this.precioTotal = precioTotal;
	}

	public void agregarItem(Item item) {
		this.items.add(item);
	}
	
	public void eliminarItem(Item item) {
		this.items.remove(item);
	}

	public EntidadOperacion getProveedor() {
		return proveedor;
	}
	public void setProveedor(EntidadOperacion proveedor) {
		this.proveedor = proveedor;
	}

	public Presupuesto compareTo(Presupuesto p2) {
		if(this.precioTotal < p2.precioTotal) {
			return this;
		}
		else {
			return p2;
		}
	}
}
