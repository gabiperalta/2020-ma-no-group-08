package dominio.presupuestos;

import java.util.ArrayList;

import dominio.excepciones.LaCompraNoRequierePresupuestosException;
import dominio.operaciones.OperacionEgreso;

public class CriterioCantidadPresupCargada implements CriterioLicitacion{
	
	boolean resultado;

	@Override
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		if(operacion.getPresupuestosNecesarios() != 0) {      //Verifico que la compra requiera presupuestos 
			resultado = operacion.getPresupuestosNecesarios() !=0; //Verifico que este cargada la cantidad de presupuestos
			return resultado;
		}
		else {
			throw new LaCompraNoRequierePresupuestosException("La compra no requiere presupuestos");
		}
			
	}
	
	@Override
	public String descripcion() {
		String descripcion;
		if(resultado) {
			descripcion = "Criterio de cantidad de presupuestos: Valido";
		}
		else {
			descripcion = "Criterio de cantidad de presupuestos: Invalido";
		}
		
		return descripcion;
	}
	
}
