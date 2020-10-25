package dominio.operaciones;

import dominio.entidades.Organizacion;
import dominio.operaciones.medioDePago.MedioDePago;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class OperacionEgreso implements Operacion {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private String identificadorOperacion;

	@OneToMany(cascade = CascadeType.PERSIST) @JoinColumn(name = "identificadorOperacion")
	private ArrayList<Item> items;

	@Convert(converter = MedioDePago.class)
	private MedioDePago medioDePago;

	@OneToOne(cascade = CascadeType.PERSIST)
	private DocumentoComercial documento;

	@Convert(converter = Date.class)
	private Date fecha;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EntidadOperacion entidadOrigen;

	@ManyToOne(cascade = CascadeType.PERSIST)
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
		this.identificadorOperacion = null;
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
	
	public void setIdentificador(String identificadorOperacionEgreso) throws Exception {
		if(this.identificadorOperacion == null) {
			this.identificadorOperacion = identificadorOperacionEgreso;
		}
		else {
			throw new Exception("Esta operacion ya tiene un identificador.");
		}
	}
	
	public String getIdentificador() {
		return this.identificadorOperacion;
	}

	public boolean esLaOperacion(String identificadorOperacionEgreso) {
		return this.identificadorOperacion.contentEquals(identificadorOperacionEgreso);
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
