package componenteVinculador.vinculador;

import com.google.gson.Gson;
import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.text.ParseException;
import java.util.ArrayList;

public class Vinculador {
    private static Vinculador shared;
    private String vinculacionJsonString;

    public  static Vinculador getInstance() {

        if (shared == null) {

            shared = new Vinculador();
        }
        return shared;
    }

    public Vinculador(){}

    public ResultadoVinculado vincular(ArrayList<OperacionVinculable> unosIngresos, ArrayList<OperacionVinculable> unosEgresos,
                                       ArrayList<CriterioVinculacion> unosCriterios) throws ParseException {

        ResultadoVinculado resultado = new ResultadoVinculado();

        for (CriterioVinculacion criterioVinculacion:unosCriterios) {
            ArrayList<Vinculacion> vinculaciones = criterioVinculacion.ejecutar(unosIngresos, unosEgresos);
            resultado.agregarVinculaciones(criterioVinculacion.getTipoCriterio(), vinculaciones);
        }

        vinculacionJsonString = new Gson().toJson(resultado);
        System.out.print("Vinculacion:\n" + vinculacionJsonString + "\n");

        return resultado;
    }

    public String getVinculacionJsonString() {
        return vinculacionJsonString;
    }
}
