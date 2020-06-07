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

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getCodigoIGJ() {
        return codigoIGJ;
    }

    public void setCodigoIGJ(String codigoIGJ) {
        this.codigoIGJ = codigoIGJ;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
}
