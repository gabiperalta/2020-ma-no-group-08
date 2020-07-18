package dominio.vinculacion.criterio;

import dominio.operaciones.OperacionEgreso;

import java.util.Comparator;

public class OrdenarEgresosPorValor implements Comparator<OperacionEgreso> {

    @Override
    public int compare(OperacionEgreso o1, OperacionEgreso o2) {
        return (int)(o1.getValorOperacion() - o2.getValorOperacion());
    }
}
