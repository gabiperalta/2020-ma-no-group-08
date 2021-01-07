package dominio.licitacion.criterioSeleccion;

import dominio.licitacion.Presupuesto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CriterioSeleccionDeProveedor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "criterio")
public abstract class CriterioSeleccionDeProveedor {
    public Presupuesto presupuestoElegido(List<Presupuesto> presupuestos) {
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public int getId() {
        return 0;
    }

    public void setId(int id) {
    }
}
