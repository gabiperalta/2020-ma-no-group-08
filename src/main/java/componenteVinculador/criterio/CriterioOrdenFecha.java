package componenteVinculador.criterio;

import componenteVinculador.vinculable.OperacionVinculable;
import java.util.Comparator;

public class CriterioOrdenFecha implements Comparator<OperacionVinculable> {

    @Override
    public int compare(OperacionVinculable op1, OperacionVinculable op2) {
        return op1.getFecha().compareTo(op2.getFecha());
    }
}
