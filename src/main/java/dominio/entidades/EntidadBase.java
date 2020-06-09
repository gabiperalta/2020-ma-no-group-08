package dominio.entidades;

public class EntidadBase {

    protected String descripcion;
    protected String nombreFicticio;
    protected EntidadJuridica entidadJuridica;


    public EntidadBase( String descripcion, String nombreFicticio, EntidadJuridica entidadJuridica
    ){
        this.entidadJuridica = entidadJuridica;
        this.descripcion = descripcion;
        this.nombreFicticio = nombreFicticio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
