package dominio.presupuestos;

import java.util.ArrayList;

import dominio.operaciones.OperacionEgreso;

public class ResultadoLicitacion {
	OperacionEgreso operacion;
	ArrayList<Presupuesto> presupuestos;
	boolean resultado;
	String descripcion;
	
	public ResultadoLicitacion(){
		presupuestos = new ArrayList<Presupuesto>();
	}
	
	// TODO, Depende de como termine estando definida la validacion, que constructor vamos a usar
	public ResultadoLicitacion(OperacionEgreso unaOperacion, ArrayList<Presupuesto> unosPresupuestos, boolean unResultado, String unaDescripcion){
		operacion = unaOperacion;
		presupuestos = unosPresupuestos;
		resultado = unResultado;
		descripcion = unaDescripcion;
	}
	
	public OperacionEgreso getOperacion() {
		return operacion;
	}
	
	public void setOperacion(OperacionEgreso operacion) {
		this.operacion = operacion;
	}
	
	public ArrayList<Presupuesto> getPresupuestos() {
		return presupuestos;
	}
	
	public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}
	
	public boolean isResultado() {
		return resultado;
	}
	
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
