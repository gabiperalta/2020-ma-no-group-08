package componenteVinculador.criterio.orden;

import componenteVinculador.vinculable.OperacionVinculable;

public class CriterioOrdenFecha implements CriterioOrden {

    @Override
    public int compare(OperacionVinculable op1, OperacionVinculable op2) {
        return op1.getFecha().compareTo(op2.getFecha());
    }
}
