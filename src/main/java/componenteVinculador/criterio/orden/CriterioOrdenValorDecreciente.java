package componenteVinculador.criterio.orden;

import componenteVinculador.vinculable.OperacionVinculable;

public class CriterioOrdenValorDecreciente implements CriterioOrden {

    @Override
    public int compare(OperacionVinculable op1, OperacionVinculable op2) {
        return (int)(op2.getMonto() - op1.getMonto());
    }
}