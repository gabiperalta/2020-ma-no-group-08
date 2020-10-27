package dominio.licitacion.criterioSeleccion;

import dominio.licitacion.Presupuesto;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "criterio")
public interface CriterioSeleccionDeProveedor {
    public Presupuesto presupuestoElegido(ArrayList<Presupuesto> presupuestos);

    @Id
    public int getId();

    public void setId(int id);
}
