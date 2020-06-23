package dominio.presupuestos;

import java.util.ArrayList;
import java.util.Collections;


import dominio.excepciones.LaCompraNoRequierePresupuestosException;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private OperacionEgreso compra; 
	private ArrayList<Presupuesto> presupuestos;
	private int presupuestosNecesarios;

	public Licitacion(OperacionEgreso compra,int presupNec){
		this.compra = compra;
		this.presupuestosNecesarios = presupNec;
	}
	
	public ArrayList<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public int getPresupuestosNecesarios() {
		return presupuestosNecesarios;
	}

	public void setPresupuestosNecesarios(int presupuestosNecesarios) {
		this.presupuestosNecesarios = presupuestosNecesarios;
	}
	public void agregarPresupuesto(Presupuesto presup) {
		this.presupuestos.add(presup);
	}
	
	public void sacarPresupuesto(Presupuesto presup) {
		this.presupuestos.remove(presup);
	}
	
	public boolean cantidadPresupuestosCargada(OperacionEgreso operacion,ArrayList<Presupuesto> presupuestos) {
		if(operacion.getPresupuestosNecesarios() != 0) {      //Verifico que la compra requiera presupuestos 
			return this.getPresupuestosNecesarios() !=0; //Verifico que este cargada la cantidad de presupuestos
		}
		else {
			throw new LaCompraNoRequierePresupuestosException("La compra no requiere presupuestos");
		}
	}
	
	public boolean criterioMenorPrecio(ArrayList<Presupuesto> presupuestos) {
		Collections.sort(presupuestos, new OrdenarPorPrecio());
		if(presupuestos.get(0).getMontoTotal() < presupuestos.get(1).getMontoTotal()) {
			return true;
		}
		else {
			return false;
		}
		// TODO, ACA HAY QUE ARREGLAR CALCULAR EL RESULTADO Y SETEARLO
	}
	
	public boolean criterioPresupuestosCorrespondientes(OperacionEgreso operacion, ArrayList<Presupuesto> presupuestos) {
		return this.presupuestosNecesarios == operacion.getPresupuestosNecesarios();
	}
	
	public boolean cantidadItemsValida(OperacionEgreso operacion) {
		return this.getPresupuestos().stream().allMatch(p->p.esValido(operacion));
	}
	
//	public void generarResultado() {
//		
//		// suscripcion.getUsuarios().forEach( cuentaUsuario -> cuentaUsuario.getBandejaDeMensajes().nuevoMensaje(Mensaje));
//		
//		boolean resultado = criterios.stream().allMatch( criterio -> criterio.validar(compra, presupuestos) );
//		
//		String descripcionCriterios = criterios.stream().map(criterio -> criterio.descripcion()).collect(Collectors.joining("\n"));
//		
//		Mensaje Mensaje = new Mensaje(descripcionCriterios,false);
//		
//		NotificadorSuscriptores.getInstance().notificar(Mensaje);
//	}
//	public String descripcion() {
//		String descripcion;
//		if(resultado) {
//			descripcion = "Criterio de cantidad de presupuestos: Valido";
//		}
//		else {
//			descripcion = "Criterio de cantidad de presupuestos: Invalido";
//		}
//		
//		return descripcion;
//	}
	
//	public String descripcion() {
//		return resultado?"Criterio de menor precio: Valido":"Criterio de menor precio: Invalido";
//	}
	
	
}
