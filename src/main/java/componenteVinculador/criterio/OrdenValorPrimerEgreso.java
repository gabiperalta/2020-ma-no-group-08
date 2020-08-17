package componenteVinculador.criterio;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;

public class OrdenValorPrimerEgreso extends CriterioVinculacion {

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenValor();
    }
 }
