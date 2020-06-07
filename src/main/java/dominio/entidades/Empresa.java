package dominio.entidades;

public class Empresa extends EntidadJuridica{

    protected ETipoEmpresa tipo;
    protected Integer cantidadPersonal;
    protected EActividad actividad;
    protected Double promedioVentas;


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

    public EActividad getActividad() {
        return actividad;
    }

    public void setActividad(EActividad actividad) {
        this.actividad = actividad;
    }

    public double getPromedioVentas() {
        return promedioVentas;
    }

    public void setPromedioVentas(double promedioVentas) {
        this.promedioVentas = promedioVentas;
    }
}
