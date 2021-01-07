package dominio.licitacion.criterioSeleccion;

import dominio.licitacion.OrdenarPorPrecio;
import dominio.licitacion.Presupuesto;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("menor_precio")
//@Proxy(proxyClass = CriterioSeleccionDeProveedor.class)
public class CriterioMenorPrecio extends CriterioSeleccionDeProveedor {

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Presupuesto presupuestoElegido(List<Presupuesto> presupuestos) {
        return presupuestos.stream().min(new OrdenarPorPrecio()).get();
    }
}
