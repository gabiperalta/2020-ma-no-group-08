package dominio.operaciones;

import dominio.operaciones.medioDePago.MedioDePago;

import java.util.ArrayList;
import java.util.Date;

public class OperacionEgresoBuilder {
	private ArrayList<Item> items;
	private MedioDePago medioDePago;
	private DocumentoComercial documento;
	private Date fecha;
	private double valorOperacion;
	private EntidadOperacion entidadOrigen;
	private EntidadOperacion entidadDestino;
	  
	  
	// public OperacionEgresoBuilder(ArrayList<Item>items, MedioDePago medioPago, DocumentoComercial doc,
	//		 					   Date fecha, double valorOperacion, EntidadOperacion origen, EntidadOperacion destino) {
	//	 this.setItems(items);
	//	 this.setMedioDePago(medioPago);
	//	 this.setDocumento(doc);
	//	 this.setFecha(fecha);
	//	 this.setValorOperacion(valorOperacion);
	//	 this.setEntidadOrigen(origen);
	//	 this.setEntidadDestino(destino);
	// }


	public ArrayList<Item> getItems() {
		return items;
	}


	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}


	public MedioDePago getMedioDePago() {
		return medioDePago;
	}


	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}


	public DocumentoComercial getDocumento() {
		return documento;
	}


	public void setDocumento(DocumentoComercial documento) {
		this.documento = documento;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public double getValorOperacion() {
		return valorOperacion;
	}


	public void setValorOperacion(double valorOperacion) {
		this.valorOperacion = valorOperacion;
	}


	public EntidadOperacion getEntidadOrigen() {
		return entidadOrigen;
	}


	public void setEntidadOrigen(EntidadOperacion entidadOrigen) {
		this.entidadOrigen = entidadOrigen;
	}


	public EntidadOperacion getEntidadDestino() {
		return entidadDestino;
	}


	public void setEntidadDestino(EntidadOperacion entidadDestino) {
		this.entidadDestino = entidadDestino;
	}
	 
	public OperacionEgreso build() {
		return new OperacionEgreso(items,medioDePago,documento,fecha,valorOperacion,entidadOrigen,entidadDestino);
	}


}
