package componenteVinculador.criterio;

import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CriterioOrdenFechaTest {

    private CriterioOrdenFecha target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable ingreso3;
    private List<OperacionVinculable> ingresos;
    private FechaUtils utils;
    @Before
    public void setUp() {
        target = new CriterioOrdenFecha();
        utils = new FechaUtils();
//        INGRESOS
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso2 = new OperacionVinculable(150,utils.obtenerFechaDiasAtras(1), ETipoOperacionVinculable.INGRESO);
        ingreso3 = new OperacionVinculable(1900,utils.obtenerFechaDiasAtras(2), ETipoOperacionVinculable.INGRESO);

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