package componenteVinculador.criterio;

import componenteVinculador.criterio.orden.CriterioOrdenFecha;
import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class CriterioOrdenFechaTest {

    private CriterioOrdenFecha target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable ingreso3;
    private ArrayList<OperacionVinculable> ingresos;

    @Before
    public void setUp() throws ParseException {
        target = new CriterioOrdenFecha();
//        INGRESOS
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso2 = new OperacionVinculable(150,FechaUtils.obtenerFechaDiasAtras(new Date(),1), ETipoOperacionVinculable.INGRESO);
        ingreso3 = new OperacionVinculable(1900,FechaUtils.obtenerFechaDiasAtras(new Date(), 2), ETipoOperacionVinculable.INGRESO);

        ingresos = new ArrayList<>();
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);
        ingresos.add(ingreso3);
    }

    @Test
    public void ordenar() {
        ingresos.sort(target);
        assertSame(ingresos.get(0), ingreso3);
    }
}