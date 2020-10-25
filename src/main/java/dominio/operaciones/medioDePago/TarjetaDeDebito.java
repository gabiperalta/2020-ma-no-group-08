package dominio.operaciones.medioDePago;

import dominio.operaciones.medioDePago.MedioDePago;

public class TarjetaDeDebito implements MedioDePago {
    String nombre;
    String numero;
    String entidadDePago;


    public TarjetaDeDebito(String nombre,String numero,     String entidadDePago){
        this.nombre = nombre;
        this.numero = numero;
        this.entidadDePago = entidadDePago;

    }

    public void informacionARegistrar() {

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

    @Override
    public int getCuotas() {
        return 0;
    }

    @Override
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
        return false;
    }

    @Override
    public boolean getEsTarjetaDeDebito() {
        return true;
    }
}
