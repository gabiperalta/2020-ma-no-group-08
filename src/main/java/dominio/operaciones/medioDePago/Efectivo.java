package dominio.operaciones.medioDePago;

public class Efectivo implements MedioDePago {
    double monto;
    String puntoDePago;

    public Efectivo(double monto,String puntoDePago){
        this.monto = monto;
        this.puntoDePago = puntoDePago;
    }

	public void informacionARegistrar() {

    }
}
