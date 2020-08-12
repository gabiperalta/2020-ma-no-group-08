package componenteVinculador.vinculable;

import java.util.Date;

public class OperacionVinculable implements Vinculable {

    private double monto;
    private Date fecha;
    private ETipoOperacionVinculable tipoOperacion;

    public OperacionVinculable (double unMonto, Date unaFecha, ETipoOperacionVinculable tipoOperacion) {
        this.monto = unMonto;
        this.fecha = unaFecha;
        this.tipoOperacion = tipoOperacion;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public Date getFecha() {
        return fecha;
    }

    public ETipoOperacionVinculable getTipoOperacion() {
        return tipoOperacion;
    }

    @Override
    public boolean sePuedeVincularA(ETipoOperacionVinculable unTipoOperacion, double montoAcumulado) {
        return (monto > montoAcumulado) && (tipoOperacion != unTipoOperacion) && (tipoOperacion == ETipoOperacionVinculable.INGRESO);
    }

    public boolean esIgualA(OperacionVinculable operacionVinculable) {
        return monto == operacionVinculable.getMonto() &&
                fecha == operacionVinculable.getFecha() &&
                tipoOperacion == operacionVinculable.getTipoOperacion();

    }
}
