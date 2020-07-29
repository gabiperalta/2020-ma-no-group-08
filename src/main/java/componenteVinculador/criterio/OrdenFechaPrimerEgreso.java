package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public class OrdenFechaPrimerEgreso implements CriterioVinculacion {

    @Override
    public void ejecutar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
       this.ordenar(ingresos,egresos);
    }

    @Override
    public void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        CriterioOrdenFecha criterio = new CriterioOrdenFecha();
        ingresos.sort(criterio);
        egresos.sort(criterio);
    }

    @Override
    public ArrayList<ResultadoVinculado> getResultadosVinculados() {

        return null;
    }
}
