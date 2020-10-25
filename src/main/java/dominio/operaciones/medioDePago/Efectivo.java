package dominio.operaciones.medioDePago;

public class Efectivo implements MedioDePago {
    double monto;
    String puntoDePago;
    String nombre;
    String entidadDePago;


    public Efectivo(double monto,String puntoDePago, String nombre, String entidadDePago){
        this.monto = monto;
        this.puntoDePago = puntoDePago;
        this.nombre = nombre;
        this.entidadDePago = entidadDePago;

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

    @Override
    public boolean getEsDineroEnCuenta() {
        return false;
    }

    @Override
    public boolean getEsEfectivo() {
        return true;
    }

    @Override
    public boolean getEsTarjetaDeCredito() {
        return false;
    }

    @Override
    public boolean getEsTarjetaDeDebito() {
        return false;
    }
}
