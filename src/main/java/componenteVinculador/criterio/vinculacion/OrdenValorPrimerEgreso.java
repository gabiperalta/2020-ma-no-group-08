package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.criterio.orden.CriterioOrdenValorDecreciente;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class OrdenValorPrimerEgreso extends CriterioVinculacion {

    public OrdenValorPrimerEgreso(Object parametroCondicion) {
        super(parametroCondicion);
    }

    @Override
    void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(new CriterioOrdenValor());
        egresos.sort(getCriterioOrden());
    }

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenValorDecreciente();
    }

    @Override
    public ETipoCriterioVinculacion getTipoCriterio() {
        return ETipoCriterioVinculacion.VALOR_EGRESO;
    }
 }
