package dominio.licitacion;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.criterioSeleccion.CriterioSeleccionDeProveedor;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private OperacionEgreso compra; 
	private ArrayList<Presupuesto> presupuestos;
	private boolean finalizada;
	private boolean resultadoCantPresupCargada;
	private boolean resultadoSeleccionDeProveedor;
	private boolean resultadoPresupCorresp;
	private CriterioSeleccionDeProveedor criterioSeleccionDeProveedor;

	public Licitacion(OperacionEgreso compra){
		this.compra = compra;
		this.presupuestos = new ArrayList<>();
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

	public void agregarCriterioSeleccionDeProveedor(CriterioSeleccionDeProveedor criterioSeleccionDeProveedor){
		this.criterioSeleccionDeProveedor = criterioSeleccionDeProveedor;
	}

	private boolean cumpleCriterioCantidadPresupuestos(OperacionEgreso operacion) {
		resultadoCantPresupCargada = operacion.getPresupuestosNecesarios() == this.presupuestos.size();
		return resultadoCantPresupCargada;
	}
	
	public boolean cantidadItemsValida(OperacionEgreso operacion) {
		return this.getPresupuestos().stream().allMatch(p->p.esValido(operacion));
	}
	
	private boolean cumpleCriterioCorrespondeYSeleccionDeProveedor(OperacionEgreso operacion) {
		Presupuesto presupuestoCorrespondiente = presupuestos.stream().filter(p->p.esCorrespondiente(operacion))
																	  .findFirst()
																	  .get();
		
		resultadoPresupCorresp = presupuestoCorrespondiente!=null;
		resultadoSeleccionDeProveedor = criterioSeleccionDeProveedor.presupuestoElegido(presupuestos) == presupuestoCorrespondiente;
		return resultadoPresupCorresp && resultadoSeleccionDeProveedor;
}
	
	public String descripcionCantPresupuestos() {
		return resultadoCantPresupCargada?"Criterio de cantidad de presupuestos: Valido":"Criterio de cantidad de presupuestos: Invalido";
	}
	
	public String descripcionPresupCorresp() {
		return resultadoPresupCorresp?"Criterio presupuesto correspondiente: Valido":"Criterio presupuesto correspondiente: Invalido";
	}
	
	public String descripcionSeleccionDeProveedor() {
		return resultadoSeleccionDeProveedor?"Criterio de seleccion de proveedor: Valido":"Criterio de seleccion de proveedor: Invalido";
	}
	
	public String mensajeTexto() {
		return this.descripcionCantPresupuestos() + "\n" + this.descripcionSeleccionDeProveedor() + "\n" + this.descripcionPresupCorresp();
	}
	
	public void licitar () {
		this.cumpleCriterioCorrespondeYSeleccionDeProveedor(compra);
		this.cumpleCriterioCantidadPresupuestos(compra);
		this.finalizada = true;
		NotificadorSuscriptores notificador = NotificadorSuscriptores.getInstance();
		notificador.notificar(this.mensajeTexto(),this);
	}
	
	public boolean puedeLicitar() {
		return this.cumpleCriterioCantidadPresupuestos(compra) && 
			   this.cumpleCriterioCorrespondeYSeleccionDeProveedor(compra);
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
