package componenteVinculador.vinculable;

import java.util.Date;

interface Vinculable {
    double getMonto();
    Date getFecha();
    ETipoOperacionVinculable getTipoOperacion();
    boolean sePuedeVincularA(ETipoOperacionVinculable vinculable, double montoAcumulado);
}
