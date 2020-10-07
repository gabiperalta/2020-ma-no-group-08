package dominio.operaciones;

import dominio.entidades.Organizacion;

import java.util.Date;

public interface Operacion {

    public String getIdentificador();
    public boolean esLaOperacion(String identificadorEntidadCategorizable);
    public double getMontoTotal();
    public boolean esIngreso();
    public boolean esDeLaOrganizacion(Organizacion unaOrganizacion);
}
