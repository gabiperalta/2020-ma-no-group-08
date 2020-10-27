package dominio.operaciones.medioDePago;

import javax.persistence.Entity;

//@Entity
public class Efectivo extends MedioDePago {
    double monto;
    String puntoDePago;
    String nombre;

    public Efectivo(){}

    public Efectivo(double monto,String puntoDePago, String nombre){
        super();
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
