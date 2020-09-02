package dominio.licitacion.criterioSeleccion;

import dominio.licitacion.OrdenarPorPrecio;
import dominio.licitacion.Presupuesto;

import java.util.ArrayList;

public class CriterioMenorPrecio implements CriterioSeleccionDeProveedor {
    @Override
    public Presupuesto presupuestoElegido(ArrayList<Presupuesto> presupuestos) {
        return presupuestos.stream().min(new OrdenarPorPrecio()).get();
    }
}
