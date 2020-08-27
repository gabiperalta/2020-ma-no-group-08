package componenteVinculador.vinculador;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.criterio.vinculacion.ETipoCriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class Vinculador {
    private ArrayList<CriterioVinculacion> criterios;
    private static Vinculador shared;

    public  static Vinculador getInstance() {

        if (shared == null) {

            shared = new Vinculador();
        }
        return shared;
    }

    private Vinculador(){
        criterios = new ArrayList<>();
    }

    public void vincular(ArrayList<OperacionVinculable> unosIngresos, ArrayList<OperacionVinculable> unosEgresos,
                         ArrayList<CriterioVinculacion> unosCriterios) {
        criterios = unosCriterios;

        for (CriterioVinculacion criterioVinculacion:criterios) {
            criterioVinculacion.ejecutar(unosIngresos, unosEgresos);
        }
    }

    public ArrayList<Vinculacion> getVinculacionesPorCriterio(ETipoCriterioVinculacion tipoCriterio){
        for (CriterioVinculacion criterioVinculacion:criterios) {
            if (criterioVinculacion.getTipoCriterio() == tipoCriterio) {
                return criterioVinculacion.getResultadosVinculados();
            }
        }
        return null;
    }

}
