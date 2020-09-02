package componenteVinculador.vinculador;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class Vinculador {
    private static Vinculador shared;

    public  static Vinculador getInstance() {

        if (shared == null) {

            shared = new Vinculador();
        }
        return shared;
    }

    private Vinculador(){}

    public ResultadoVinculado vincular(ArrayList<OperacionVinculable> unosIngresos, ArrayList<OperacionVinculable> unosEgresos,
                                       ArrayList<CriterioVinculacion> unosCriterios) {

        ResultadoVinculado resultado = new ResultadoVinculado();

        for (CriterioVinculacion criterioVinculacion:unosCriterios) {
            ArrayList<Vinculacion> vinculaciones = criterioVinculacion.ejecutar(unosIngresos, unosEgresos);
            resultado.agregarVinculaciones(criterioVinculacion.getTipoCriterio(), vinculaciones);
        }

        return resultado;
    }

}
