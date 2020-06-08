package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;

public class Presupuesto{
	private EntidadOperacion proveedor;
	private ArrayList<Item> items;
	private int montoTotal;
	
	
	public double getMontoTotal() {
		return this.items.stream()
	      			.mapToInt(item -> item.getValor())
	      			.sum();
	}

	public void setmontoTotal(int montoTotal) {
		this.montoTotal = montoTotal;
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

	public int compareTo(Presupuesto p2) {
		if(this.montoTotal < p2.montoTotal) {
			return this.montoTotal;
		}
		else {
			return p2.montoTotal;
		}
	}

}
