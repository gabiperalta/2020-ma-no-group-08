package dominio.operaciones.medioDePago;

public class DineroEnCuenta implements MedioDePago {
    double monto;
    String nombre;

    public DineroEnCuenta(double monto,String nombre){
        this.monto = monto;
        this.nombre = nombre;
    }

    public void informacionARegistrar() {

    }
}
