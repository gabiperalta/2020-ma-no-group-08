package componenteVinculador.criterio.orden;

import componenteVinculador.vinculable.OperacionVinculable;

import java.util.Comparator;

public interface CriterioOrden extends Comparator<OperacionVinculable> {
    int compare(OperacionVinculable op1, OperacionVinculable op2);

    }
