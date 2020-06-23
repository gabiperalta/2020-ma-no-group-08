package seguridad;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ValidadorContraseniaTest {

    private ArrayList<String> contrasenias;
    private ValidadorContrasenia target;

    @Before
    public void init() throws Exception {
        target =  new ValidadorContrasenia();

        contrasenias = new ArrayList<String>();
        contrasenias.add("Contrasenia1!");
        contrasenias.add("Contrasenia2´");
        contrasenias.add("Contrasenia3/");
    }

    @Test
    public void esContraseniaValida() throws Exception {
        assertTrue(target.esContraseniaValida("Contrasenia4*", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorSerDeLasPeores() throws Exception {
        assertFalse(target.esContraseniaValida("danielle", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorNoRespetarExpresionRegularPorCaracterEspecial() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia1", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorNoRespetarExpresionRegularPorNumeros() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia+", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorNoRespetarExpresionRegularPorMayusculas() throws Exception {
        assertFalse(target.esContraseniaValida("contrasenia1+", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorNoRespetarExpresionRegularPorEspacio() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia1+ ", contrasenias));
    }

    @Test
    public void esContraseniaInvalidaPorSerRepetida() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia3/", contrasenias));
    }

}