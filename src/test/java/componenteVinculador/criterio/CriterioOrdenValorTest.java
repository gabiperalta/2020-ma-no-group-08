package componenteVinculador.criterio;

import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CriterioOrdenValorTest {
    private CriterioOrdenValor target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable ingreso3;
    private List<OperacionVinculable> ingresos;

    @Before
    public void setUp() {
        target = new CriterioOrdenValor();

//        INGRESOS
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso2 = new OperacionVinculable(150,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso3 = new OperacionVinculable(1900,new Date(), ETipoOperacionVinculable.INGRESO);

        ingresos = new ArrayList<>();
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);
        ingresos.add(ingreso3);
    }

    @Test
    public void ordenar() {
        ingresos.sort(target);

        assertSame(ingresos.get(0), ingreso2);
    }
}