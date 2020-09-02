package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public class OrdenValorPrimerIngreso extends CriterioVinculacion {

    public OrdenValorPrimerIngreso(Object parametroCondicion) {
        super(parametroCondicion);
    }

    @Override
    public ArrayList<Vinculacion> ejecutar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        ordenar(ingresos,egresos, getCriterioOrden());
        return vincular(egresos,ingresos);
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
