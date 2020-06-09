package dominio.presupuestos;

import java.util.ArrayList;

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
}
