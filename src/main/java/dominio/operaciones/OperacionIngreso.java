package dominio.operaciones;

import dominio.entidades.Organizacion;

import javax.persistence.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "operaciones_ingreso")
public class OperacionIngreso implements Operacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	private String descripcion;
	private double montoTotal;

	//@Convert(converter = Date.class)
	private Date fecha;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EntidadOperacion entidadOrigen;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EntidadOperacion entidadDestino;


	public EntidadOperacion getEntidadOrigen() {
		return entidadOrigen;
	}

	public double getMontoTotal(){
		return montoTotal;
	}

	public boolean puedenVincularse(List <OperacionEgreso> egresos) {
		return true;
	}

	public String getIdentificador() {
		return "OI-" + Integer.toString(this.id);
	}

	public boolean esLaOperacion(String identificadorOperacionEgreso) {
		return this.getIdentificador().contentEquals(identificadorOperacionEgreso);
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public Date getFecha() {
		return fecha;
	}

	public String getFechaString() {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");

		return formateador.format(fecha);
	}


	public boolean esIngreso() {
		return true;
	}

	public EntidadOperacion getEntidadDestino() {
		return entidadDestino;
	}

	public void setEntidadDestino(EntidadOperacion entidadDestino) {
		this.entidadDestino = entidadDestino;
	}
	public void setEntidadOrigen(EntidadOperacion entidadOrigen) {
		this.entidadOrigen = entidadOrigen;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public boolean esDeLaOrganizacion(Organizacion unaOrganizacion) {
		return unaOrganizacion.existeLaEntidad(this.entidadDestino.getNombre());
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
