package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;

public class Presupuesto implements Comparable{
	private EntidadOperacion proveedor;
	private final ArrayList<Item> items;
	private int montoTotal;

	public Presupuesto(EntidadOperacion proveedor){
		this.proveedor = proveedor;
		items = new ArrayList<Item>();
	}
	
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

	@Override
	public int compareTo(Object presupuesto) {
		double compararMontoTotal=((Presupuesto)presupuesto).getMontoTotal();
		return (int)(this.getMontoTotal()-compararMontoTotal);
	}

	@Override
	public String toString() {
		return "[ montoTotal=" + getMontoTotal() + "]";
	}
}
