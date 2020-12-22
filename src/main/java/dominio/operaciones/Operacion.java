package dominio.operaciones;

import dominio.entidades.Organizacion;

import javax.persistence.*;
import java.util.Date;

public interface Operacion {
    public String getIdentificador();
    public abstract double getMontoTotal();
    public abstract Date getFecha();
    public abstract boolean esDeLaOrganizacion(Organizacion unaOrganizacion);
}