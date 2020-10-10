package dominio.operaciones.medioDePago;

public class Efectivo implements MedioDePago {
    double monto;
    String puntoDePago;

    public Efectivo(double monto,String puntoDePago){
        this.monto = monto;
        this.puntoDePago = puntoDePago;
    }

	public void informacionARegistrar() {

    }

    @Override
    public String getNombre() {
        return null;
    }

    @Override
    public double getMonto() {
        return monto;
    }

    public String getPuntoDePago() {
        return puntoDePago;
    }

    @Override
    public int getCuotas() {
        return 0;
    }

    @Override
    public String getNumero() {
        return null;
    }
}
