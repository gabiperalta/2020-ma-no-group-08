package dominio.operaciones;

import dominio.entidades.Organizacion;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Operacion(){}

    public String getIdentificador(){ return Integer.toString(id); }
    public abstract boolean esLaOperacion(String identificadorEntidadCategorizable);
    public abstract double getMontoTotal();
    public abstract boolean esIngreso();
    public abstract Date getFecha();
    public abstract boolean esDeLaOrganizacion(Organizacion unaOrganizacion);
}
