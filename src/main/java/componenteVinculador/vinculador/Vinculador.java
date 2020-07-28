package componenteVinculador.vinculador;

import componenteVinculador.criterio.CriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class Vinculador {
    private ArrayList<OperacionVinculable> ingresos;
    private ArrayList<OperacionVinculable> egresos;

    public Vinculador (ArrayList<OperacionVinculable> unosIngresos, ArrayList<OperacionVinculable> unosEgresos){
        this.ingresos = unosIngresos;
        this.egresos = unosEgresos;
    }

    public void vincular(ArrayList<CriterioVinculacion> criteriosVinculacion){

    }
}
