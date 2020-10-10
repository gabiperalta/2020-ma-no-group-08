package dominio.operaciones.medioDePago;

public interface MedioDePago {
    public void informacionARegistrar();
    public String getNombre();
    public double getMonto();
    public String getPuntoDePago();
    public int getCuotas();
    public String getNumero();
}
