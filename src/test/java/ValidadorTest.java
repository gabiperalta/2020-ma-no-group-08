import junit.framework.TestCase;
import seguridad.ValidadorContrasenia;

import java.util.ArrayList;

public class ValidadorTest extends TestCase {
    private ArrayList<String> contrasenias;
    private ValidadorContrasenia target;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        target =  new ValidadorContrasenia();

        contrasenias = new ArrayList<String>();
        contrasenias.add("Contrasenia1!");
        contrasenias.add("Contrasenia2´");
        contrasenias.add("Contrasenia3/");
    }

    public void testContraseniaValida() throws Exception {
        assertTrue(target.esContraseniaValida("Contrasenia4*", contrasenias));
    }

    public void testContraseniaInvalidaPorSerDeLasPeores() throws Exception {
        assertFalse(target.esContraseniaValida("danielle", contrasenias));
    }

    public void testContraseniaInvalidaPorNoRespetarExpresionRegularPorCaracterEspecial() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia1", contrasenias));
    }

    public void testContraseniaInvalidaPorNoRespetarExpresionRegularPorNumeros() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia+", contrasenias));
    }

    public void testContraseniaInvalidaPorNoRespetarExpresionRegularPorMayusculas() throws Exception {
        assertFalse(target.esContraseniaValida("contrasenia1+", contrasenias));
    }

    public void testContraseniaInvalidaPorNoRespetarExpresionRegularPorEspacio() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia1+ ", contrasenias));
    }

    public void testContraseniaInvalidaPorSerRepetida() throws Exception {
        assertFalse(target.esContraseniaValida("Contrasenia3/", contrasenias));
    }


}
