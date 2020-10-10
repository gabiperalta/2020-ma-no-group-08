package dominio.operaciones.medioDePago;

public class DineroEnCuenta implements MedioDePago {
    double monto;

    public String getNombre() {
        return nombre;
    }

    String nombre;

    public DineroEnCuenta(double monto,String nombre){
        this.monto = monto;
        this.nombre = nombre;
    }

    public void informacionARegistrar() {

    }

    public String getNombre() {
        return nombre;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String getPuntoDePago() {
        return null;
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
