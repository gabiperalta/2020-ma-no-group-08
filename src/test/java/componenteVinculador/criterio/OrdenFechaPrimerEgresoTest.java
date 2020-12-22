package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.vinculacion.OrdenFechaPrimerEgreso;
import componenteVinculador.criterio.vinculacion.OrdenValorPrimerEgreso;
import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class OrdenFechaPrimerEgresoTest {
    private OrdenFechaPrimerEgreso target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable egreso1;
    private OperacionVinculable egreso2;
    private OperacionVinculable egreso3;
    private OperacionVinculable egreso4;
    private ArrayList<OperacionVinculable> ingresos;
    private ArrayList<OperacionVinculable> egresos;

    @Before
    public void setUp() throws Exception {
        target = (OrdenFechaPrimerEgreso) GeneradorCriterio.generarCriterio("Fecha_Egreso",5);
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

        ArrayList<Vinculacion> vinculaciones = target.ejecutar(ingresos,egresos);

        Vinculacion resultado1 = new Vinculacion(ingreso1);
        resultado1.vincularNuevoEgreso(egreso2);
        resultado1.vincularNuevoEgreso(egreso3);

        Vinculacion resultado2 = new Vinculacion(ingreso2);
        resultado2.vincularNuevoEgreso(egreso1);

        assertTrue(resultado1.equals(vinculaciones.get(0)) && resultado2.equals(vinculaciones.get(1)));
    }

    @Test
    public void vinculacionConEgresoSinVincularPorMontoOk() {
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);

        egresos.add(egreso4);

        ArrayList<Vinculacion> vinculaciones = target.ejecutar(ingresos,egresos);
        assertEquals(vinculaciones.size(),0);
    }

    @Test
    public void vinculacionSinIngresos() {
        egresos.add(egreso1);
        egresos.add(egreso2);
        egresos.add(egreso3);

        ArrayList<Vinculacion> vinculaciones = target.ejecutar(ingresos,egresos);
        assertEquals(vinculaciones.size(),0);
    }

    @Test
    public void vinculacionSinEgresos() {
        ingresos.add(ingreso1);
        ingresos.add(ingreso2);

        ArrayList<Vinculacion> vinculaciones = target.ejecutar(ingresos,egresos);
        assertEquals(vinculaciones.size(),0);

    }
}