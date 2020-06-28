package dominio.licitacion;

import java.util.ArrayList;

import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;

public class Presupuesto {
	private EntidadOperacion proveedor;
	private final ArrayList<Item> items;
	private double montoTotal;
	
	public Presupuesto(EntidadOperacion proveedor, ArrayList<Item> items, double monto){
		this.proveedor = proveedor;
		this.items = items;
		this.montoTotal = monto;
	}
	
	public ArrayList<Item> getItems() {
		return items;
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
	public String toString() {
		return "[ montoTotal=" + getMontoTotal() + "]";
	}

	public boolean esValido(OperacionEgreso operacion) {
		return operacion.getItems().containsAll(this.getItems());
	}
}
