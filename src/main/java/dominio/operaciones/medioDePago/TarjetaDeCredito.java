package dominio.operaciones.medioDePago;

import dominio.operaciones.medioDePago.MedioDePago;

import javax.persistence.Entity;

//@Entity
public class TarjetaDeCredito extends MedioDePago {
    int cuotas;
    String nombre;
    String numero;
    String entidadDePago;

    public TarjetaDeCredito(){}

    public TarjetaDeCredito(int cuotas,String nombre,String numero, String entidadDePago){
        super();
        this.cuotas = cuotas;
        this.nombre = nombre;
        this.numero = numero;
        this.entidadDePago = entidadDePago;

    }


    public void informacionARegistrar() {

    }

    public int getCuotas() {
        return cuotas;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getMonto() {
        return 0;
    }

    @Override
    public String getPuntoDePago() {
        return null;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean getEsDineroEnCuenta() {
        return false;
    }

    @Override
    public boolean getEsEfectivo() {
        return false;
    }

    @Override
    public boolean getEsTarjetaDeCredito() {
        return true;
    }

    @Override
    public boolean getEsTarjetaDeDebito() {
        return false;
    }
}
