package dominio.operaciones;

import java.util.Date;
import java.util.List;

public class OperacionIngreso {
	private String descripcion;
	private double montoTotal;
	private Date fecha;
	private EntidadOperacion entidadOrigen;
	private EntidadOperacion entidadDestino;
	
	
	
	public EntidadOperacion getEntidadOrigen() {
		return entidadOrigen;
	}



	public boolean puedenVincularse(List <OperacionEgreso> egresos) {
		
	}
}
