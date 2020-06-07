import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import seguridad.administradorDeSesion.AdministradorDeSesion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;

public class InicioSesionTest {
	AdministradorDeSesion administradorSesion;
	
	@Before
	public void init() {
		administradorSesion = new AdministradorDeSesion();
	}
	
	@Test
	public void testInicioDeSesionExitosa() throws CredencialesNoValidasException {
		administradorSesion.logIn("admin1", "1234");
	}
	
	@Test
	public void testInicioDeSesionCuentaInexistente() throws CredencialesNoValidasException {
		assertThrows(CredencialesNoValidasException.class, ()->{administradorSesion.logIn("admin1", "1234");});
	}
	
	@Test
	public void testInicioDeSesionContraseniaIncorrecta() throws CredencialesNoValidasException {
		assertThrows(CredencialesNoValidasException.class, ()->{administradorSesion.logIn("admin1", "1234");});
	}
}