package dominio.operaciones;

import dominio.categorizacion.EntidadCategorizable;
import dominio.operaciones.medioDePago.MedioDePago;

import java.util.ArrayList;
import java.util.Date;

public class OperacionEgreso implements EntidadCategorizable{
	private ArrayList<Item> items;
	private MedioDePago medioDePago;
	private DocumentoComercial documento;
	private Date fecha;
	private EntidadOperacion entidadOrigen;
	private EntidadOperacion entidadDestino;
	private int presupuestosNecesarios;
	
	public OperacionEgreso(ArrayList<Item> items2, MedioDePago medioDePago2, DocumentoComercial documento2, Date fecha2,
						   EntidadOperacion entidadOrigen2, EntidadOperacion entidadDestino2, int presupuestosNecesarios) {
		this.items = items2;
		this.medioDePago = medioDePago2;
		this.documento = documento2;
		this.fecha = fecha2;
		this.entidadOrigen = entidadOrigen2;
		this.entidadDestino = entidadDestino2;
		this.presupuestosNecesarios = presupuestosNecesarios;
	}
	
	public void agregarItem(Item item) {
		this.items.add(item);
	}
	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public double getValorOperacion() {
		return this.items.stream()
  			   .mapToInt(item -> item.getValor())
  			   .sum();
	}

	public int getPresupuestosNecesarios() {
		return presupuestosNecesarios;
	}

	public void setPresupuestosNecesarios(int presupuestosNecesarios) {
		this.presupuestosNecesarios = presupuestosNecesarios;
	}
	public EntidadOperacion getEntidadOrigen() {
		return entidadOrigen;
	}
	public EntidadOperacion getEntidadDestino() {
		return entidadDestino;
	}
	
}
