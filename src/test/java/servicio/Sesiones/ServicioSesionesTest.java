package servicio.Sesiones;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import seguridad.sesion.exceptions.CredencialesNoValidasException;

public class ServicioSesionesTest {

	ServicioSesiones servicioSesion;
	
	@Before
	public void init() {
		servicioSesion = new ServicioSesiones();
	}
	
	@Test
	public void testInicioDeSesionExitosa() throws CredencialesNoValidasException {
		servicioSesion.logIn("admin1", "1234");
		assertTrue(true);
	}
	
	@Test
	public void testInicioDeSesionCuentaInexistente() throws NoSuchElementException {
		assertThrows(NoSuchElementException.class, ()->{servicioSesion.logIn("admin12", "1234");});
	}
	
	@Test
	public void testInicioDeSesionContraseniaIncorrecta() throws CredencialesNoValidasException {
		assertThrows(CredencialesNoValidasException.class, ()->{servicioSesion.logIn("admin1", "12345");});
	}
	
	@Test
	public void testInicioDeSesionCuentaBloqueada() throws CredencialesNoValidasException {
		assertThrows(CredencialesNoValidasException.class, ()->{servicioSesion.logIn("admin2", "12345");});
		assertThrows(CredencialesNoValidasException.class, ()->{servicioSesion.logIn("admin2", "12344");});
		assertThrows(CredencialesNoValidasException.class, ()->{servicioSesion.logIn("admin2", "12348");});
		assertThrows("Esta cuenta esta bloqueada", CredencialesNoValidasException.class, ()->{servicioSesion.logIn("admin2", "Dejame entrar");});
	}
}
