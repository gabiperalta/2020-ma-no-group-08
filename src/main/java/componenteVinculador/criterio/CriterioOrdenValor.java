package componenteVinculador.criterio;

import componenteVinculador.vinculable.OperacionVinculable;
import java.util.Comparator;

class CriterioOrdenValor implements Comparator<OperacionVinculable> {

    @Override
    public int compare(OperacionVinculable op1, OperacionVinculable op2) {
        return (int)(op1.getMonto() - op2.getMonto());
    }
}
