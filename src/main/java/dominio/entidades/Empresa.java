package dominio.entidades;

public class Empresa extends EntidadJuridica{

    protected ETipoEmpresa tipo;
    protected Integer cantidadPersonal;
    protected String actividad;
    protected Double promedioVentas;


    public Empresa(ETipoEmpresa tipo, Integer cantidadPersonal, String actividad, Double promedioVentas, String razonSocial,
                   String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal
    ){
        this.tipo = tipo;
        this.cantidadPersonal = cantidadPersonal;
        this.actividad = actividad;
        this.promedioVentas = promedioVentas;
        this.razonSocial = razonSocial;
        this.nombreFicticio = nombreFicticio;
        this.cuit = cuit;
        this.codigoIGJ = codigoIGJ;
        this.direccionPostal = direccionPostal;


    }

    public void categorizar() {


    }

    public ETipoEmpresa getTipo() {
        return tipo;
    }

    public void setTipo(ETipoEmpresa tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(Integer cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public double getPromedioVentas() {
        return promedioVentas;
    }

    public void setPromedioVentas(double promedioVentas) {
        this.promedioVentas = promedioVentas;
    }
}
