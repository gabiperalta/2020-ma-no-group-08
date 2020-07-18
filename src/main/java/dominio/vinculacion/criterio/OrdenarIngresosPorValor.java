package dominio.vinculacion.criterio;

import dominio.operaciones.OperacionIngreso;

import java.util.Comparator;

public class OrdenarIngresosPorValor implements Comparator<OperacionIngreso> {

    @Override
    public int compare(OperacionIngreso o1, OperacionIngreso o2) {
        return (int)(o1.getMontoTotal() - o2.getMontoTotal());
    }
}
