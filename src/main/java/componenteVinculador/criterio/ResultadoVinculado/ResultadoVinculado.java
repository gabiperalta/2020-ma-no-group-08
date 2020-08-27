package componenteVinculador.criterio.ResultadoVinculado;

import componenteVinculador.criterio.vinculacion.ETipoCriterioVinculacion;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultadoVinculado {

    private HashMap<ETipoCriterioVinculacion,ArrayList<Vinculacion>> vinculacionesHashMap;

    public ResultadoVinculado() {
        vinculacionesHashMap = new HashMap<>();
    }

    public void agregarVinculaciones(ETipoCriterioVinculacion tipoCriterio, ArrayList<Vinculacion> vinculaciones){
        vinculacionesHashMap.put(tipoCriterio,vinculaciones);
    }

    public ArrayList<Vinculacion> getVinculaciones(ETipoCriterioVinculacion tipoCriterio) {
        return vinculacionesHashMap.get(tipoCriterio);
    }

}
