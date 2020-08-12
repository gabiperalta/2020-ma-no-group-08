package componenteVinculador.criterio.ResultadoVinculado;

import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class ResultadoVinculado {
    private OperacionVinculable ingreso;
    private ArrayList<OperacionVinculable> egresos;

    public ResultadoVinculado (OperacionVinculable unIngreso){
        ingreso = unIngreso;
        ArrayList<OperacionVinculable> egresos = new ArrayList<OperacionVinculable>();
    }

    public void vincularNuevoEgreso(OperacionVinculable egreso) {
        if (puedeVincularEgreso(egreso)){
            egresos.add(egreso);
        }
    }

    public boolean puedeVincularEgreso(OperacionVinculable egreso) {
        return !egresos.contains(egreso) && egreso.getTipoOperacion() == ETipoOperacionVinculable.EGRESO;
    }

    public boolean contieneAlIngreso(OperacionVinculable unIngreso){
        return ingreso.esIgualA(unIngreso);
    }

    public boolean sePuedeVincularEgreso(OperacionVinculable egreso) {
        return ingreso.sePuedeVincularA(egreso.getTipoOperacion(),getMontoAcumulado());
    }

    public ArrayList<OperacionVinculable> getEgresos() {
        return egresos;
    }

    private double getMontoAcumulado () {
        if (egresos.size() == 0) {
            return 0;
        }
        return egresos.stream().mapToDouble(OperacionVinculable::getMonto).sum();
    }
}
