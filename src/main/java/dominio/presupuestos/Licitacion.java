package dominio.presupuestos;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private OperacionEgreso compra; 
	private ArrayList<Presupuesto> presupuestos;
	private ArrayList<CriterioLicitacion> criterios;

	public Licitacion(OperacionEgreso compra,ArrayList<Presupuesto> presupuestos,ArrayList<CriterioLicitacion> criterios){
		this.compra = compra;
		this.presupuestos = presupuestos;
		this.criterios = criterios;
		// TODO revisar si vamos a crear las listas antes o aca
	}
	
	public void generarResultado() {
		
		// suscripcion.getUsuarios().forEach( cuentaUsuario -> cuentaUsuario.getBandejaDeMensajes().nuevoMensaje(resultadoLicitacion));
		
		boolean resultado = criterios.stream().allMatch( criterio -> criterio.validar(compra, presupuestos) );
		
		String descripcionCriterios = criterios.stream().map((criterio -> criterio.descripcion(compra, presupuestos))).collect(Collectors.joining("\n"));
		
		ResultadoLicitacion resultadoLicitacion = new ResultadoLicitacion(compra, presupuestos, resultado, descripcionCriterios);
		
		NotificadorSuscriptores.getInstance().notificar(resultadoLicitacion);
	}
}
