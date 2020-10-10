package dominio.entidades;

public abstract class EntidadJuridica {

    protected String razonSocial;
    protected String nombreFicticio;
    protected String cuit;
    protected String codigoIGJ;
    protected String direccionPostal;

    public void agregarEntidadBase(EntidadBase entidadBase){
        entidadBase.setEntidadJuridica(this);
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getCuit(){
        return cuit;
    }

    public String getDireccionPostal(){
        return direccionPostal;
    }

    public void recategorizar(double cantidadPersonalNuevo, double ventasPromedioNuevo) {}

}
