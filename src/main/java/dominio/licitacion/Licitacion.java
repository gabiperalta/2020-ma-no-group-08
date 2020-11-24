package dominio.licitacion;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import auditoria.RepoAuditorias;
import dev.morphia.Datastore;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.criterioSeleccion.CriterioSeleccionDeProveedor;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Licitacion{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int identificadorLicitacion;

	@OneToOne
	private OperacionEgreso compra;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Presupuesto> presupuestos;

	private boolean finalizada;
	private boolean resultadoCantPresupCargada;
	private boolean resultadoSeleccionDeProveedor;
	private boolean resultadoPresupCorresp;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "criterio_id")
	private CriterioSeleccionDeProveedor criterioSeleccionDeProveedor;

	@Transient
	private NotificadorSuscriptores notificadorSuscriptores;

	@Transient
	private ArrayList<CuentaUsuario> suscriptores;

	@Transient
	private int ultimoIdentificadorPresupuesto;

	public Licitacion(){

	}

	public Licitacion(OperacionEgreso compra, NotificadorSuscriptores notificadorSuscriptores){
		this.compra = compra;
		this.presupuestos = new ArrayList<>();
		this.suscriptores = new ArrayList<>();
		this.finalizada = false;
		this.notificadorSuscriptores = notificadorSuscriptores;
		this.ultimoIdentificadorPresupuesto = 1;
	}

	//public ArrayList<Presupuesto> getPresupuestos() {
	//	return (ArrayList<Presupuesto>) presupuestos;
	//}
	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void agregarPresupuesto(Presupuesto presup, String nombreUsuario, Datastore datastore) {
		if(presup.esValido(compra)) {
			presup.setEntidadOrigen(this.compra.getEntidadOrigen());
			//presup.setIdentificador(Objects.toString(this.getIdentificador(),"") + "-P" + ultimoIdentificadorPresupuesto);
			this.presupuestos.add(presup);

			//Auditoria
			new RepoAuditorias(datastore).registrarAlta(this.getIdentificadorConEtiqueta() + "-" + Integer.toString(ultimoIdentificadorPresupuesto), nombreUsuario);

			ultimoIdentificadorPresupuesto++;
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
		Presupuesto presupuestoCorrespondiente;

		try{
			presupuestoCorrespondiente= presupuestos.stream().filter(p->p.esCorrespondiente(operacion))
					.findFirst()
					.get();
		}catch (NoSuchElementException e){
			presupuestoCorrespondiente = null;
		}


		resultadoPresupCorresp = presupuestoCorrespondiente!=null;
		if(presupuestoCorrespondiente != null)
			resultadoSeleccionDeProveedor = criterioSeleccionDeProveedor.presupuestoElegido((ArrayList<Presupuesto>)getPresupuestos()) == presupuestoCorrespondiente;
		else
			resultadoSeleccionDeProveedor = false;
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
		try {
			notificadorSuscriptores.notificar(this.mensajeTexto(), this.suscriptores);
		}
		catch (NullPointerException e){

		}

	}

	public boolean puedeLicitar() {
		return this.cumpleCriterioCantidadPresupuestos(compra) &&
				this.cumpleCriterioCorrespondeYSeleccionDeProveedor(compra);
	}

	public void suscribir(CuentaUsuario cuenta) throws Exception {
		if(this.validarSuscripcion()) {
			suscriptores.add(cuenta);
		}
		else {
			throw new Exception("Licitacion cerrada, no puede suscribir.");
		}
	}

	private boolean validarSuscripcion(){
		return !this.finalizada;
	}

	public void setIdentificador(String identificadorLicitacion){
		//if(this.identificadorLicitacion == null) {
		//	this.identificadorLicitacion = identificadorLicitacion;
		//	presupuestos.forEach(presupuesto -> {
		//		if(presupuesto.getIdentificador().startsWith("-"))
		//			presupuesto.setIdentificador(this.getIdentificador() + presupuesto.getIdentificador());
		//	});
		//}
	}

	public int getIdentificadorLicitacion() {
		return identificadorLicitacion;
	}

	public void setIdentificadorLicitacion(int identificadorLicitacion) {
		this.identificadorLicitacion = identificadorLicitacion;
	}

	public String getIdentificadorConEtiqueta() {
		return "L-" + identificadorLicitacion;
	}

	public OperacionEgreso getOperacionEgreso(){
		return compra;
	}

	public boolean estaFinalizada(){
		return finalizada;
	}
}