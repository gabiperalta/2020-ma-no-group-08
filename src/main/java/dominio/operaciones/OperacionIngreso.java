package dominio.operaciones;

import java.util.Date;
import java.util.List;

public class OperacionIngreso{
	private String identificadorOperacion;
	private String descripcion;
	private double montoTotal;
	private Date fecha;
	private EntidadOperacion entidadOrigen;
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
	
	public void setIdentificador(String identificadorOperacionIngreso) throws Exception {
		if(this.identificadorOperacion == null) {
			this.identificadorOperacion = identificadorOperacionIngreso;
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
	
	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public EntidadOperacion getEntidadDestino() {
		return entidadDestino;
	}
	
}
