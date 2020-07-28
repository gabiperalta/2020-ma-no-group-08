package componenteVinculador.vinculable;

import java.util.Date;

public class OperacionVinculable implements Vinculable {

    private double monto;
    private Date fecha;

    public OperacionVinculable (double unMonto, Date unaFecha) {
        this.monto = unMonto;
        this.fecha = unaFecha;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public Date getFecha() {
        return fecha;
    }
}
