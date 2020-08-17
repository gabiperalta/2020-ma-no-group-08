package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public class CriterioVinculacion {
//    TODO: agregar rango de fecha
//    TODO: agregar metodo que devuelva el tipo
    protected ArrayList<ResultadoVinculado> resultados;

    public CriterioVinculacion() {
        resultados = new ArrayList<>();
    }

    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos) {

    }
    public void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(criterioOrden);
        egresos.sort(criterioOrden);
    }

    public ArrayList<ResultadoVinculado> getResultadosVinculados(){
        return resultados;
    }


}
