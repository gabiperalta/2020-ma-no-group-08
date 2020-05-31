package dominio.operaciones;

public class TarjetaDeDebito implements MedioDePago{
    String nombre;
    String numero;

    public TarjetaDeDebito(String nombre,String numero){
        this.nombre = nombre;
        this.numero = numero;
    }

    public void informacionARegistrar() {

    }
}
