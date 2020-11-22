package dominio.entidades;

import dominio.entidades.calculadorFiscal.CategorizadorFiscal;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import dominio.operaciones.EntidadOperacion;

import javax.persistence.*;

@Entity
public class Empresa {

    @Id @GeneratedValue
    private String id;

    protected String razonSocial;

    @Enumerated(EnumType.STRING)
    protected ETipoEmpresa tipo;

    protected double cantidadPersonal;

    @Enumerated(EnumType.STRING)
    protected ETipoActividad actividad;

    protected double promedioVentas;
    protected boolean esComisionista;
    protected String nombreFicticio;
    protected String cuit;
    protected String codigoIGJ;
    protected String direccionPostal;

    @OneToOne
    private EntidadOperacion entidadOperacion;

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

    public Empresa() {

    }

    public void recategorizar(double cantidadPersonalNuevo, double ventasPromedioNuevo) {
        CategorizadorFiscal categorizadorFiscal = new CategorizadorFiscal();
        tipo = categorizadorFiscal.recategorizar(actividad, esComisionista, cantidadPersonalNuevo, ventasPromedioNuevo);
        cantidadPersonal = cantidadPersonalNuevo;
        promedioVentas = ventasPromedioNuevo;
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

}
