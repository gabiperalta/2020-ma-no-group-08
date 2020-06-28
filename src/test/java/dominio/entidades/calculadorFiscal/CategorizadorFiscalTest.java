package dominio.entidades.calculadorFiscal;

import dominio.entidades.ETipoEmpresa;
import org.junit.Before;
import org.junit.Test;

import static dominio.entidades.calculadorFiscal.ETipoActividad.*;
import static org.junit.Assert.*;

public class CategorizadorFiscalTest {
    private CategorizadorFiscal target;

    @Before
    public void setUp() throws Exception {
        target = new CategorizadorFiscal();
    }

    @Test
    public void recategorizarEmpresaComisionistaMicro() {
        assertEquals(target.recategorizar(CONSTRUCCION,true,5,0), ETipoEmpresa.MICRO);
    }

    @Test
    public void recategorizarEmpresaComisionistaPequenia() {
        assertEquals(target.recategorizar(SERVICIOS,true,15,0), ETipoEmpresa.PEQUENIA);
    }

    @Test
    public void recategorizarEmpresaComisionistaMedianaT1() {
        assertEquals(target.recategorizar(COMERCIO,true,100,0), ETipoEmpresa.MEDIANA_T1);
    }

    @Test
    public void recategorizarEmpresaComisionistaMedianaT2() {
        assertEquals(target.recategorizar(INDMIN,true,300,0), ETipoEmpresa.MEDIANA_T2);
    }

    @Test
    public void recategorizarEmpresaNoComisionistaMicro() {
        assertEquals(target.recategorizar(SERVICIOS,false,0,1000000), ETipoEmpresa.MICRO);
    }

    @Test
    public void recategorizarEmpresaNoComisionistaPequenia() {
        assertEquals(target.recategorizar(SERVICIOS,false,0,25000000), ETipoEmpresa.PEQUENIA);
    }

    @Test
    public void recategorizarEmpresaNoComisionistaMedianaT1() {
        assertEquals(target.recategorizar(SERVICIOS,false,0,200000000), ETipoEmpresa.MEDIANA_T1);
    }

    @Test
    public void recategorizarEmpresaNoComisionistaMedianaT2() {
        assertEquals(target.recategorizar(SERVICIOS,false,0,1300000000), ETipoEmpresa.MEDIANA_T2);
    }
}