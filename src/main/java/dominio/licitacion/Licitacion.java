package dominio.licitacion;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private OperacionEgreso compra; 
	private ArrayList<Presupuesto> presupuestos;
	private boolean finalizada;
	private boolean resultadoCantPresupCargada;
	private boolean resultadoMenorPrecio;
	private boolean resultadoPresupCorresp;

	public Licitacion(OperacionEgreso compra){
		this.compra = compra;
		this.presupuestos = new ArrayList<Presupuesto>();
		this.finalizada = false;
	}
	
	public ArrayList<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	
	public void agregarPresupuesto(Presupuesto presup) {
		if(presup.esValido(compra)) {
			this.presupuestos.add(presup);
		}
	}
	
	public void sacarPresupuesto(Presupuesto presup) {
		this.presupuestos.remove(presup);
	}
	
	private boolean cumpleCriterioCantidadPresupuestos(OperacionEgreso operacion) {
		resultadoCantPresupCargada = operacion.getPresupuestosNecesarios() == this.presupuestos.size();
		return resultadoCantPresupCargada;
	}
	
//	private boolean cumpleCriterioMenorPrecio(ArrayList<Presupuesto> presupuestos) {
//		//Collections.sort(presupuestos, new OrdenarPorPrecio());
//		 Presupuesto presupuestoElegido = presupuestos.stream()
//										 .min(new OrdenarPorPrecio())
//										 .get();
//		if(presupuestoElegido.getMontoTotal() < presupuestos.get(1).getMontoTotal()) {
//			resultadoMenorPrecio = true;
//			return resultadoMenorPrecio;
//		}
//		else {
//			resultadoMenorPrecio = false;
//			return resultadoMenorPrecio;
//		}
//		// TODO, ACA HAY QUE ARREGLAR CALCULAR EL RESULTADO Y SETEARLO
//	}
	
//	private boolean cumpleCriterioPresupuestosCorrespondientes(OperacionEgreso operacion) {
//		resultadoPresupCorresp =  this.presupuestosNecesarios == operacion.getPresupuestosNecesarios();
//		return resultadoPresupCorresp;
//	}
	
	public boolean cantidadItemsValida(OperacionEgreso operacion) {
		return this.getPresupuestos().stream().allMatch(p->p.esValido(operacion));
	}
	
	private boolean cumpleCriterioCorrespondeYEsMenorPrecio(OperacionEgreso operacion) {
		Presupuesto presupuestoElegidoMenorPrecio = presupuestos.stream()
				 									.min(new OrdenarPorPrecio())
				 									.get();
		
		Presupuesto presupuestoCorrespondiente = presupuestos.stream().filter(p->p.esCorrespondiente(operacion))
																	  .findFirst()
																	  .get();
		
		resultadoPresupCorresp = presupuestoCorrespondiente!=null;
		
		resultadoMenorPrecio = presupuestoElegidoMenorPrecio == presupuestoCorrespondiente;
		
		return resultadoPresupCorresp && resultadoMenorPrecio;
}
	
	public String descripcionCantPresupuestos() {
		return resultadoCantPresupCargada?"Criterio de cantidad de presupuestos: Valido":"Criterio de cantidad de presupuestos: Invalido";
	}
	
	public String descripcionPresupCorresp() {
		return resultadoPresupCorresp?"Criterio presupuesto correspondiente: Valido":"Criterio presupuesto correspondiente: Invalido";
	}
	
	public String descripcionMenorPrecio() {
		return resultadoMenorPrecio?"Criterio de menor precio: Valido":"Criterio de menor precio: Invalido";
	}
	
	public String mensajeTexto() {
		return this.descripcionCantPresupuestos() + "\n" + this.descripcionMenorPrecio() + "\n" + this.descripcionPresupCorresp();
	}
	
	public void licitar () {
		this.cumpleCriterioCorrespondeYEsMenorPrecio(compra);
		this.cumpleCriterioCantidadPresupuestos(compra);
		this.finalizada = true;
		NotificadorSuscriptores notificador = NotificadorSuscriptores.getInstance();
		notificador.notificar(this.mensajeTexto(),this);
	}
	
	public boolean puedeLicitar() {
		return this.cumpleCriterioCantidadPresupuestos(compra) && 
			   this.cumpleCriterioCorrespondeYEsMenorPrecio(compra);
	}
	
	public void suscribir(CuentaUsuario cuenta) throws Exception {
		if(this.finalizada) {
			NotificadorSuscriptores.getInstance().suscribir(cuenta, this);
		}
		else {
			throw new Exception("Licitacion cerrada, no puede suscribir.");
		}
	}
	
}
