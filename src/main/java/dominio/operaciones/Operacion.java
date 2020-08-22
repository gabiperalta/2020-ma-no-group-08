package dominio.operaciones;

import java.util.Date;

public interface Operacion {

    public double getMontoTotal();
    public Date getFecha();
    public boolean esIngreso();
}
