package dominio.entidades;

import dominio.entidades.calculadorFiscal.CategorizadorFiscal;
import dominio.entidades.calculadorFiscal.ETipoActividad;

public class Empresa extends EntidadJuridica{

    protected ETipoEmpresa tipo;
    protected double cantidadPersonal;
    protected ETipoActividad actividad;
    protected double promedioVentas;
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

    public void recategorizar(double cantidadPersonalNuevo, double ventasPromedioNuevo) {
        CategorizadorFiscal categorizadorFiscal = new CategorizadorFiscal();
        tipo = categorizadorFiscal.recategorizar(actividad, esComisionista, cantidadPersonalNuevo, ventasPromedioNuevo);
        cantidadPersonal = cantidadPersonalNuevo;
        promedioVentas = ventasPromedioNuevo;
    }

}
