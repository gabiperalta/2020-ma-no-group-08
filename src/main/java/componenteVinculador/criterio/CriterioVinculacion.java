package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public interface CriterioVinculacion {
    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos);
    public void ordenar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos);
    public ArrayList<ResultadoVinculado> getResultadosVinculados();
}
