package componenteVinculador.vinculable;


import java.util.Date;

public class GeneradorVinculable {

    public static OperacionVinculable generarVinculable(double monto, Date fecha, boolean esIngreso) {
        return new OperacionVinculable(monto, fecha, esIngreso ? ETipoOperacionVinculable.INGRESO : ETipoOperacionVinculable.EGRESO);
    }
}
