package dominio.operaciones.medioDePago;

public class Efectivo implements MedioDePago {
    double monto;
    String puntoDePago;
    String nombre;

    public Efectivo(double monto,String puntoDePago, String nombre){
        this.monto = monto;
        this.puntoDePago = puntoDePago;
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void informacionARegistrar() {

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
