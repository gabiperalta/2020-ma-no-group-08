package dominio.operaciones;

import java.util.ArrayList;
import java.util.Date;

public class OperacionEgreso {
	private ArrayList<Item> items;
	private MedioDePago medioDePago;
	private DocumentoComercial documento;
	private Date fecha;
	private double valorOperacion;
	private EntidadOperacion entidadOrigen;
	private EntidadOperacion entidadDestino;

	public OperacionEgreso(ArrayList<Item> items2, MedioDePago medioDePago2, DocumentoComercial documento2, Date fecha2,
						   double valorOperacion2, EntidadOperacion entidadOrigen2, EntidadOperacion entidadDestino2) {
		this.items = items2;
		this.medioDePago = medioDePago2;
		this.documento = documento2;
		this.fecha = fecha2;
		this.valorOperacion = valorOperacion2;
		this.entidadOrigen = entidadOrigen2;
		this.entidadDestino = entidadDestino2;
	}

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

}
