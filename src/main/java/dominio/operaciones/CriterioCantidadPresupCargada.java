package dominio.operaciones;

import java.util.ArrayList;

import dominio.excepciones.LaCompraNoRequierePresupuestosException;
import dominio.presupuestos.CriterioLicitacion;
import dominio.presupuestos.Presupuesto;

public class CriterioCantidadPresupCargada implements CriterioLicitacion{

	@Override
	public boolean validar(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		if(operacion.getPresupuestosNecesarios() != 0) {      //Verifico que la compra requiera presupuestos 
			return operacion.getPresupuestosNecesarios() !=0; //Verifico que este cargada la cantidad de presupuestos
		}
		else {
			throw new LaCompraNoRequierePresupuestosException("La compra no requiere presupuestos");
		}
			
	}
	
}
