package dominio.entidades;

public class OSC extends EntidadJuridica {

    public OSC(String razonSocial,
                   String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal
    ){
        this.razonSocial = razonSocial;
        this.nombreFicticio = nombreFicticio;
        this.cuit = cuit;
        this.codigoIGJ = codigoIGJ;
        this.direccionPostal = direccionPostal;


    }


}
