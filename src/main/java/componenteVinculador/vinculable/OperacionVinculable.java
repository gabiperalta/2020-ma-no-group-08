package componenteVinculador.vinculable;

import componenteVinculador.vinculable.utils.FechaUtils;

import java.util.Date;
import java.util.Objects;

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
    public boolean sePuedeVincularA(ETipoOperacionVinculable unTipoOperacion, double montoAcumulado, int rangoDias, Date fechaEgreso) {
        return (monto > montoAcumulado) && (tipoOperacion != unTipoOperacion) &&
                (tipoOperacion == ETipoOperacionVinculable.INGRESO) &&
                FechaUtils.estaDentroDelRango(getFecha(),fechaEgreso,rangoDias);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacionVinculable that = (OperacionVinculable) o;
        return Double.compare(that.monto, monto) == 0 &&
                Objects.equals(fecha, that.fecha) &&
                tipoOperacion == that.tipoOperacion;
    }
}
