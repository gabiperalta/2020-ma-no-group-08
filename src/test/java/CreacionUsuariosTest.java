import org.junit.Before;
import org.junit.Test;

import seguridad.administradorDeSesion.AdministradorDeSesion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_usuarios.ServicioABMUsuarios;

public class CreacionUsuariosTest {
	AdministradorDeSesion administradorSesion;
	ServicioABMUsuarios abmUsuarios;
	
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException {
		administradorSesion = new AdministradorDeSesion();
		
		administradorSesion.logIn("admin1", "1234");
		
		abmUsuarios = administradorSesion.getSesion().abmUsuarios();
		
		// CREAR UNA ORGANIZACION
	}
	
	@Test
	public void testCreacionUsuarioValida() {
		
	}
	
	@Test
	public void testCreacionUsuarioNombreUsuarioYaUtilizado() {
		
	}
	
	@Test
	public void testCreacionUsuarioNoExisteLaOrganizacion() {
		
	}
	
}
