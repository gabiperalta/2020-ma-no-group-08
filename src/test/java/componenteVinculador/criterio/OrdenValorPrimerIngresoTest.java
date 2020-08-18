package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.vinculacion.OrdenValorPrimerEgreso;
import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class OrdenValorPrimerIngresoTest {
    private OrdenValorPrimerEgreso target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable egreso1;
    private OperacionVinculable egreso2;
    private OperacionVinculable egreso3;
    private OperacionVinculable egreso4;
    private ArrayList<OperacionVinculable> ingresos;
    private ArrayList<OperacionVinculable> egresos;

    @Before
    public void setUp() {
        target = new OrdenValorPrimerEgreso();
        ingresos = new ArrayList<>();
        egresos = new ArrayList<>();

        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso2 = new OperacionVinculable(2000,new Date(), ETipoOperacionVinculable.INGRESO);

        egreso1 = new OperacionVinculable(500, FechaUtils.obtenerFechaDiasAtras(new Date(),1), ETipoOperacionVinculable.EGRESO);
        egreso2 = new OperacionVinculable(300, FechaUtils.obtenerFechaDiasAtras(new Date(),4), ETipoOperacionVinculable.EGRESO);
        egreso3 = new OperacionVinculable(350, FechaUtils.obtenerFechaDiasAtras(new Date(),3), ETipoOperacionVinculable.EGRESO);
        egreso4 = new OperacionVinculable(2100,new Date(), ETipoOperacionVinculable.EGRESO);
    }
    @Test
    public void vinculacionConDosResultadosOk() {
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);

        egresos.add(egreso1);
        egresos.add(egreso2);
        egresos.add(egreso3);

        target.ejecutar(ingresos,egresos,5);

        ResultadoVinculado resultado1 = new ResultadoVinculado(ingreso1);
        resultado1.vincularNuevoEgreso(egreso2);
        resultado1.vincularNuevoEgreso(egreso3);

        ResultadoVinculado resultado2 = new ResultadoVinculado(ingreso2);
        resultado2.vincularNuevoEgreso(egreso1);

        assertTrue(resultado1.equals(target.getResultadosVinculados().get(0)) && resultado2.equals(target.getResultadosVinculados().get(1)));
    }

    @Test
    public void vinculacionConEgresoSinVincularPorMontoOk() {
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);

        egresos.add(egreso4);

        target.ejecutar(ingresos,egresos,5);
        assertEquals(target.getResultadosVinculados().size(),0);
    }

    @Test
    public void vinculacionSinIngresos() {
        egresos.add(egreso1);
        egresos.add(egreso2);
        egresos.add(egreso3);

        target.ejecutar(ingresos,egresos,5 );
        assertEquals(target.getResultadosVinculados().size(),0);
    }

    @Test
    public void vinculacionSinEgresos() {
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);

        target.ejecutar(ingresos,egresos,5 );
        assertEquals(target.getResultadosVinculados().size(),0);
    }
}