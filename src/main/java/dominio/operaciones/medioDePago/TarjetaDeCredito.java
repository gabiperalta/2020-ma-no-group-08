package dominio.operaciones.medioDePago;

import dominio.operaciones.medioDePago.MedioDePago;

public class TarjetaDeCredito implements MedioDePago {
    int cuotas;

    public String getNombre() {
        return nombre;
    }

    String nombre;
    String numero;

    public TarjetaDeCredito(int cuotas,String nombre,String numero){
        this.cuotas = cuotas;
        this.nombre = nombre;
        this.numero = numero;
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
}
