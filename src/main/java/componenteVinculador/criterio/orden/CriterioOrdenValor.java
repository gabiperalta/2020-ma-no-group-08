package componenteVinculador.criterio.orden;

import componenteVinculador.vinculable.OperacionVinculable;

public class CriterioOrdenValor implements CriterioOrden {

    @Override
    public int compare(OperacionVinculable op1, OperacionVinculable op2) {
        return (int)(op1.getMonto() - op2.getMonto());
    }
}
