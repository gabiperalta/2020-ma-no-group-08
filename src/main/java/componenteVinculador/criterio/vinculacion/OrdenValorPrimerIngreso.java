package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public class OrdenValorPrimerIngreso extends CriterioVinculacion {

    @Override
    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos, int rangoDias) {
        ordenar(ingresos,egresos, getCriterioOrden());
        vincular(egresos,ingresos, rangoDias);
    }

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenValor();
    }

    @Override
    public ETipoCriterioVinculacion getTipoCriterio() {
        return ETipoCriterioVinculacion.VALOR_INGRESO;
    }
}