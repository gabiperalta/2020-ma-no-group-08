package servicio.abm_usuarios;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

//import dominio.cuentasUsuarios.Roles.Rol;
//import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import temporal.seguridad.repositorioUsuarios.exceptions.RolInvalidoException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

public class ServicioABMUsuariosTest {

	ServicioABMUsuarios abmUsuarios;
	// Organizacion organizacion; TODO, Debemos modificar a la cuenta de usuario nuevamente para que use a la organizacion
	EntidadOperacion entidadOperacion;
	ArrayList<String> listaDeRoles;
			
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException, UsuarioYaExistenteException {
		
		abmUsuarios = new ServicioABMUsuarios();

		// organizacion = new Organizacion("Organizacion 1");
		entidadOperacion = new EntidadOperacion("EntidadOperacionTest", "UnCuil", "UnaDireccion");
		
		listaDeRoles = new ArrayList<String>();
		
		listaDeRoles.add("ROL_ESTANDAR");
		
	}
	
	@Test
	public void testAltaUsuarioColaboradorOK() throws UsuarioYaExistenteException, RolInvalidoException {
		// abmUsuarios.altaUsuarioColaborador("usuario1", organizacion, listaDeRoles);
		abmUsuarios.altaUsuarioColaborador("usuario1", entidadOperacion, listaDeRoles);
	}
	
	@Test
	public void testAltaUsuarioColaboradorNombreYaUtilizado() throws UsuarioYaExistenteException, RolInvalidoException  {
		// abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion, listaDeRoles);
		abmUsuarios.altaUsuarioColaborador("usuarioRepetido", entidadOperacion, listaDeRoles);
		assertThrows(UsuarioYaExistenteException.class, ()->{abmUsuarios.altaUsuarioColaborador("usuarioRepetido", entidadOperacion, listaDeRoles);});
		// assertThrows(UsuarioYaExistenteException.class, ()->{abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion, listaDeRoles);});
	}
	
}