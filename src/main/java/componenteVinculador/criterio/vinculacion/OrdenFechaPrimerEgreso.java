package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenFecha;

public class OrdenFechaPrimerEgreso extends CriterioVinculacion {


    public OrdenFechaPrimerEgreso(Object parametroCondicion) {
        super(parametroCondicion);
    }

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenFecha();
    }

    @Override
    public ETipoCriterioVinculacion getTipoCriterio() {
        return ETipoCriterioVinculacion.FECHA_EGRESO;
    }
}
