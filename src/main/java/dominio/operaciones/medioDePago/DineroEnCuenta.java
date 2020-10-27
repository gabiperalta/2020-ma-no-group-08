package dominio.operaciones.medioDePago;

import javax.persistence.Entity;

//@Entity
public class DineroEnCuenta extends MedioDePago {
    double monto;
    String nombre;

    public DineroEnCuenta(){}

    public DineroEnCuenta(double monto,String nombre){
        super();
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

    @Override
    public boolean getEsDineroEnCuenta() {
        return true;
    }

    @Override
    public boolean getEsEfectivo() {
        return false;
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
