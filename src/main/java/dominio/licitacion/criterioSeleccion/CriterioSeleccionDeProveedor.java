package dominio.licitacion.criterioSeleccion;

import dominio.licitacion.Presupuesto;

import java.util.ArrayList;

public interface CriterioSeleccionDeProveedor {
    public Presupuesto presupuestoElegido(ArrayList<Presupuesto> presupuestos);
}
