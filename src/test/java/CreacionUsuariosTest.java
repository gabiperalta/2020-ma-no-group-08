import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import dominio.entidades.Organizacion;
import seguridad.administradorDeSesion.AdministradorDeSesion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import servicio.abm_usuarios.ServicioABMUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

public class CreacionUsuariosTest {
	AdministradorDeSesion administradorSesion;
	ServicioABMUsuarios abmUsuarios;
	Organizacion organizacion;
	
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException, UsuarioYaExistenteException {
		administradorSesion = new AdministradorDeSesion();
		
		administradorSesion.logIn("admin1", "1234");
		
		abmUsuarios = administradorSesion.getSesion().abmUsuarios();

		organizacion = new Organizacion("Organizacion 1");
		
		abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion);
	}
	
	@Test
	public void testCreacionUsuarioValida() throws UsuarioYaExistenteException {
		abmUsuarios.altaUsuarioColaborador("usuario1", organizacion);
	}
	
	@Test
	public void testCreacionUsuarioNombreYaUtilizado() {
		assertThrows(UsuarioYaExistenteException.class, ()->{abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion);});
	}
	
}
