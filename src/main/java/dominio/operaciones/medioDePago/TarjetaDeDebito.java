package dominio.operaciones.medioDePago;

import dominio.operaciones.medioDePago.MedioDePago;

public class TarjetaDeDebito implements MedioDePago {
    public String getNombre() {
        return nombre;
    }

    String nombre;
    String numero;

    public TarjetaDeDebito(String nombre,String numero){
        this.nombre = nombre;
        this.numero = numero;
    }

    public void informacionARegistrar() {

    }
}
