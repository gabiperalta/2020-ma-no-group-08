package componenteVinculador.vinculador;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.criterio.vinculacion.ETipoCriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class Vinculador {
    private ArrayList<CriterioVinculacion> criterios;

    public void vincular(ArrayList<OperacionVinculable> unosIngresos, ArrayList<OperacionVinculable> unosEgresos,
                         ArrayList<CriterioVinculacion> unosCriterios, int rangoDias) {
        criterios = unosCriterios;

        for (CriterioVinculacion criterioVinculacion:criterios) {
            criterioVinculacion.ejecutar(unosIngresos, unosEgresos, rangoDias);
        }
    }

    public ArrayList<ResultadoVinculado> getVinculacionesPorCriterio(ETipoCriterioVinculacion tipoCriterio){
        for (CriterioVinculacion criterioVinculacion:criterios) {
            if (criterioVinculacion.getTipoCriterio() == tipoCriterio) {
                return criterioVinculacion.getResultadosVinculados();
            }
        }
        return null;
    }

}
