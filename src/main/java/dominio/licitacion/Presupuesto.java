package dominio.licitacion;

import java.util.ArrayList;

import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;

public class Presupuesto {
	private EntidadOperacion proveedor;
	private final ArrayList<Item> items;
	private double montoTotal;
	private boolean esValido;
	
	public Presupuesto(EntidadOperacion proveedor, ArrayList<Item> unosItems, double monto){
		this.proveedor = proveedor;
		this.montoTotal = monto;
		items = new ArrayList<Item>();
		this.esValido = true;
		unosItems.forEach(i->{
			try {
				this.agregarItem(i);
			} catch (Exception e) {
				e.getMessage();
				this.esValido = false;
			}
		});
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	private void agregarItem(Item item) throws Exception {
		if(this.items.stream().anyMatch(i->i.getDescripcion() == item.getDescripcion())) {
			throw new Exception("No se puede agregar item");
		}
		this.items.add(item);
	}
	public double getMontoTotal() {
		return this.items.stream()
	      			.mapToInt(item -> item.getValor())
	      			.sum();
	}

	public void setmontoTotal(int montoTotal) {
		this.montoTotal = montoTotal;
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
//		return this.items.parallelStream().allMatch(i->i.esValido(operacion));
		return this.esValido && this.items.size() == operacion.getItems().size() && this.items.stream().allMatch(i->i.esValido(operacion));
	}

	public boolean esCorrespondiente(OperacionEgreso operacion) {
		return this.items.stream().allMatch(i->i.esCorrespondiente(operacion) && this.proveedor.correspondeEntidad(operacion.getEntidadOrigen()));
	}
}
