package componenteVinculador.vinculador;

import componenteVinculador.criterio.CriterioVinculacion;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;
import java.util.Date;

public class Vinculador {
    private ArrayList<OperacionVinculable> ingresos;
    private ArrayList<OperacionVinculable> egresos;
    private Date fechaInicial;
    private Date fechaFinal;

    public Vinculador (){
        ingresos = new ArrayList<OperacionVinculable>();
        egresos = new ArrayList<OperacionVinculable>();
    }

    public void agregarIngreso(OperacionVinculable operacionVinculable) {
        if (puedeVincularse(operacionVinculable)) {
            ingresos.add(operacionVinculable);
        }
    }

    public void agregarEgreso(OperacionVinculable operacionVinculable) {
        if (puedeVincularse(operacionVinculable)) {
            egresos.add(operacionVinculable);
        }
    }

    public void vincular(CriterioVinculacion criteriosVinculacion) {
        criteriosVinculacion.ejecutar(ingresos,egresos);
    }

    private boolean puedeVincularse(OperacionVinculable operacionVinculable) {
        return this.fechaInicial.compareTo(operacionVinculable.getFecha()) * operacionVinculable.getFecha().compareTo(this.fechaFinal) >= 0;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
