package dominio.licitacion;

import java.util.ArrayList;
import java.util.Collections;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.excepciones.LaCompraNoRequierePresupuestosException;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private OperacionEgreso compra; 
	private ArrayList<Presupuesto> presupuestos;
	private int presupuestosNecesarios;
	private boolean finalizada;
	private boolean resultadoCantPresupCargada;
	private boolean resultadoMenorPrecio;
	private boolean resultadoPresupCorresp;
	
	public Licitacion() {}
	public Licitacion(OperacionEgreso compra,int presupNec){
		this.compra = compra;
		this.presupuestosNecesarios = presupNec;
	}
	
	public ArrayList<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}
	public int getPresupuestosNecesarios() {
		return presupuestosNecesarios;
	}

	public void setPresupuestosNecesarios(int presupuestosNecesarios) {
		this.presupuestosNecesarios = presupuestosNecesarios;
	}
	public void agregarPresupuesto(Presupuesto presup) {
		if(presup.esValido(compra)) {
			this.presupuestos.add(presup);
		}
	}
	
	public void sacarPresupuesto(Presupuesto presup) {
		this.presupuestos.remove(presup);
	}
	
	public boolean cumpleCriterioCantidadPresupuestos(OperacionEgreso operacion) {
		if(operacion.getPresupuestosNecesarios() != 0) { //Verifico que la compra requiera presupuestos 
			this.resultadoCantPresupCargada = this.getPresupuestosNecesarios() !=0; //Verifico que este cargada la cantidad de presupuestos
			return resultadoCantPresupCargada; 
		}
		else {
			throw new LaCompraNoRequierePresupuestosException("La compra no requiere presupuestos");
		}
	}
	
	public boolean cumpleCriterioMenorPrecio(ArrayList<Presupuesto> presupuestos) {
		Collections.sort(presupuestos, new OrdenarPorPrecio());
		if(presupuestos.get(0).getMontoTotal() < presupuestos.get(1).getMontoTotal()) {
			resultadoMenorPrecio = true;
			return resultadoMenorPrecio;
		}
		else {
			resultadoMenorPrecio = false;
			return resultadoMenorPrecio;
		}
		// TODO, ACA HAY QUE ARREGLAR CALCULAR EL RESULTADO Y SETEARLO
	}
	
	public boolean cumpleCriterioPresupuestosCorrespondientes(OperacionEgreso operacion) {
		resultadoPresupCorresp =  this.presupuestosNecesarios == operacion.getPresupuestosNecesarios();
		return resultadoPresupCorresp;
	}
	
	public boolean cantidadItemsValida(OperacionEgreso operacion) {
		return this.getPresupuestos().stream().allMatch(p->p.esValido(operacion));
	}
	public String descripcionCantPresupuestos() {
		String descripcion;
		if(resultadoCantPresupCargada) {
			descripcion = "Criterio de cantidad de presupuestos: Valido";
		}
		else {
			descripcion = "Criterio de cantidad de presupuestos: Invalido";
		}
		
		return descripcion;
	}
	
	public String descripcionPresupCorresp() {
		String descripcion;
		if(resultadoPresupCorresp) {
			descripcion = "Criterio de cantidad de presupuestos: Valido";
		}
		else {
			descripcion = "Criterio de cantidad de presupuestos: Invalido";
		}
		
		return descripcion;
	}
	
	public String descripcionMenorPrecio() {
		return resultadoMenorPrecio?"Criterio de menor precio: Valido":"Criterio de menor precio: Invalido";
	}
	
	public Mensaje mensajeTexto() {
		String cuerpo = this.descripcionCantPresupuestos() + ";" + this.descripcionMenorPrecio() + ";" + this.descripcionPresupCorresp();
		return new Mensaje(cuerpo, false);
	}	
	
	public void licitar () {
		this.cumpleCriterioPresupuestosCorrespondientes(compra);
		this.cumpleCriterioMenorPrecio(presupuestos);
		this.cumpleCriterioCantidadPresupuestos(compra);
		NotificadorSuscriptores notificador = NotificadorSuscriptores.getInstance();
		notificador.notificar(this.mensajeTexto());
	}

	public void suscribir(CuentaUsuario cuenta) {
		NotificadorSuscriptores notificador = NotificadorSuscriptores.getInstance();
		notificador.suscribir(cuenta, this);
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

	
	
}
