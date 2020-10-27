package dominio.operaciones;

import dominio.entidades.Organizacion;
import dominio.operaciones.medioDePago.MedioDePago;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "operaciones_egreso")
public class OperacionEgreso extends Operacion {

	@OneToMany(cascade = CascadeType.PERSIST) @JoinColumn(name = "identificadorOperacion")
	private List<Item> items;

	//@Convert(converter = MedioDePago.class)
	//@OneToOne
	@Transient
	private MedioDePago medioDePago;

	@OneToOne(cascade = CascadeType.PERSIST)
	private DocumentoComercial documento;

	//@Convert(converter = Date.class)
	private Date fecha;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EntidadOperacion entidadOrigen;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EntidadOperacion entidadDestino;

	private int presupuestosNecesarios;

	public OperacionEgreso(){}
	
	public OperacionEgreso(ArrayList<Item> items2, MedioDePago medioDePago2, DocumentoComercial documento2, Date fecha2,
						   EntidadOperacion entidadOrigen2, EntidadOperacion entidadDestino2, int presupuestosNecesarios) {
		super();
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
		return new ArrayList<>(items);
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
	
	public String getIdentificador() {
		return "OE-" + super.getIdentificador();
	}

	public boolean esLaOperacion(String identificadorOperacionEgreso) {
		return this.getIdentificador().contentEquals(identificadorOperacionEgreso);
	}
	
	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public DocumentoComercial getDocumento() {
		return documento;
	}

	@Override
	public double getMontoTotal() {
		return getValorOperacion();
	}
	public String getFechaString() {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");

		return formateador.format(fecha);
	}
	@Override
	public Date getFecha() {
		return fecha;
	}

	public boolean esIngreso() {
		return false;
	}

	@Override
	public boolean esDeLaOrganizacion(Organizacion unaOrganizacion) {
		return unaOrganizacion.existeLaEntidad(this.entidadOrigen.getNombre());
	}

	public void modificarse(MedioDePago medioDePagoFinal, DocumentoComercial unDocumento, Date unaFecha, EntidadOperacion unaEntidadOrigen, EntidadOperacion unaEntidadDestino){
		medioDePago = medioDePagoFinal;
		documento = unDocumento;
		fecha = unaFecha;
		entidadOrigen = unaEntidadOrigen;
		entidadDestino = unaEntidadDestino;
	}
}
