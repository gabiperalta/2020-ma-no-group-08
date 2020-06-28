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

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
