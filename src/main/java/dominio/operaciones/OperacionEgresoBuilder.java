package dominio.operaciones;

import dominio.operaciones.medioDePago.MedioDePago;

import java.util.ArrayList;
import java.util.Date;

public class OperacionEgresoBuilder {
	private ArrayList<Item> items;
	private MedioDePago medioDePago;
	private DocumentoComercial documento;
	private Date fecha;
	private Double valorOperacion;
	private EntidadOperacion entidadOrigen;
	private EntidadOperacion entidadDestino;
	private int presupuestosNecesarios;
	  
	public OperacionEgresoBuilder agregarItems(ArrayList<Item> items) {
		this.items = items;
		return this;
	}

	public OperacionEgresoBuilder agregarMedioDePago(MedioDePago medio) {
		this.medioDePago = medio;
		return this;
	}

	public OperacionEgresoBuilder agregarDocComercial(DocumentoComercial doc) {
		this.documento = doc;
		return this;
	}

	public OperacionEgresoBuilder agregarFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}

	public OperacionEgresoBuilder agregarValorOperacion(Double valor) {
		this.valorOperacion = valor;
		return this;
	}

	public OperacionEgresoBuilder agregarEntidadOrigen(EntidadOperacion entidad) {
		this.entidadOrigen = entidad;
		return this;
	}

	public OperacionEgresoBuilder agregarEntidadDestino(EntidadOperacion entidad) {
		this.entidadDestino = entidad;
		return this;
	}

	public OperacionEgresoBuilder agregarPresupuestosNecesarios(int presupuestosNecesarios) {
		this.presupuestosNecesarios = presupuestosNecesarios;
		return this;
	}

	public OperacionEgreso build() {
		if (items == null || medioDePago == null || documento == null || fecha == null || valorOperacion == null ||
				valorOperacion <= 0 || entidadOrigen == null || entidadDestino == null || presupuestosNecesarios <= 0) {
			return null;
		}

		return new OperacionEgreso(items,medioDePago,documento,fecha,valorOperacion,entidadOrigen,entidadDestino,presupuestosNecesarios);
	}


}
