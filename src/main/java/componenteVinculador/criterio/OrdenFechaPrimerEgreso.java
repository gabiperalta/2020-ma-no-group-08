package componenteVinculador.criterio;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenFecha;

public class OrdenFechaPrimerEgreso extends CriterioVinculacion {


    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenFecha();
    }
}
