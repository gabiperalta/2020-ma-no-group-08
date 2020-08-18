package componenteVinculador.criterio.ResultadoVinculado;

import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;
import java.util.Objects;

public class ResultadoVinculado {
    private OperacionVinculable ingreso;
    private ArrayList<OperacionVinculable> egresos;

    public ResultadoVinculado (OperacionVinculable unIngreso){
        ingreso = unIngreso;
        egresos = new ArrayList();
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
        return ingreso.equals(unIngreso);
    }

    public boolean sePuedeVincularEgreso(OperacionVinculable egreso, int rangoDias) {
        return ingreso.sePuedeVincularA(egreso.getTipoOperacion(),getMontoAcumulado() + egreso.getMonto(), rangoDias, egreso.getFecha());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoVinculado that = (ResultadoVinculado) o;
        return Objects.equals(ingreso, that.ingreso) &&
                Objects.equals(egresos, that.egresos);
    }

}
