package dominio.operaciones;

import java.util.Date;

public interface Operacion {

    public String getIdentificador();
    public boolean esLaOperacion(String identificadorEntidadCategorizable);
    public double getMontoTotal();
}
