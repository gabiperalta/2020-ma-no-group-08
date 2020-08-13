package componenteVinculador.criterio;

import componenteVinculador.vinculable.ETipoOperacionVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import dominio.entidades.calculadorFiscal.CategorizadorFiscal;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrdenValorPrimerEgresoTest {
    private OrdenValorPrimerEgreso target;
    private OperacionVinculable ingreso1;
    private OperacionVinculable ingreso2;
    private OperacionVinculable egreso1;
    private OperacionVinculable egreso2;
    private OperacionVinculable egreso3;
    @Before
    public void setUp() {
        target = new OrdenValorPrimerEgreso();

//        INGRESOS
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
        ingreso1 = new OperacionVinculable(1000,new Date(), ETipoOperacionVinculable.INGRESO);
//        EGRESOS
    }
    @Test
    public void ejecutar() {
    }
}