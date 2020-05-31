package dominio.operaciones;

public class TarjetaDeCredito implements MedioDePago{
    int cuotas;
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
