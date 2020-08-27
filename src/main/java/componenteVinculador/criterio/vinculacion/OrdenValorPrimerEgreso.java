package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;

public class OrdenValorPrimerEgreso extends CriterioVinculacion {

    public OrdenValorPrimerEgreso(Object parametroCondicion) {
        super(parametroCondicion);
    }

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenValor();
    }

    @Override
    public ETipoCriterioVinculacion getTipoCriterio() {
        return ETipoCriterioVinculacion.VALOR_EGRESO;
    }
 }
