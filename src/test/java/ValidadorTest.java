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
        contrasenias.add("Gaboberna1!");
        contrasenias.add("Gaboberna2´");
        contrasenias.add("Gaboberna3/");
    }

    public void testContraseniaValida() throws Exception {
        assertTrue(target.esContraseniaValida("Gaboberna4*", contrasenias));
    }
}
