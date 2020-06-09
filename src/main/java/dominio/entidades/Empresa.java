package dominio.entidades;

import dominio.entidades.calculadorFiscal.CategorizadorFiscal;
import dominio.entidades.calculadorFiscal.ETipoActividad;

public class Empresa extends EntidadJuridica{

    protected ETipoEmpresa tipo;
    protected Integer cantidadPersonal;
    protected ETipoActividad actividad;
    protected Double promedioVentas;
    protected boolean esComisionista;


    public Empresa(ETipoEmpresa tipo, Integer cantidadPersonal, ETipoActividad actividad, Double promedioVentas, String razonSocial,
                   String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal, boolean esComisionista
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
        this.esComisionista = esComisionista;

    }

    public void categorizar() {
        CategorizadorFiscal categorizadorFiscal = new CategorizadorFiscal();
        this.setTipo(categorizadorFiscal.categorizar(this));
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

    public ETipoActividad getActividad() {
        return actividad;
    }

    public void setActividad(ETipoActividad actividad) {
        this.actividad = actividad;
    }

    public double getPromedioVentas() {
        return promedioVentas;
    }

    public void setPromedioVentas(double promedioVentas) {
        this.promedioVentas = promedioVentas;
    }

    public boolean isEsComisionista() {
        return esComisionista;
    }

    public void setEsComisionista(boolean esComisionista) {
        this.esComisionista = esComisionista;
    }
}
