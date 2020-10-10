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
}
