package servicio.abm_usuarios;

import static org.junit.Assert.*;

import java.util.ArrayList;

import dominio.entidades.Organizacion;
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
	Organizacion organizacion;
	EntidadOperacion entidadOperacion;
	ArrayList<String> listaDeRoles;
			
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException, UsuarioYaExistenteException {
		
		abmUsuarios = new ServicioABMUsuarios();

		organizacion = new Organizacion("Organizacion 1");
		
		listaDeRoles = new ArrayList<String>();
		
		listaDeRoles.add("ROL_ESTANDAR");
		
	}
	
	@Test
	public void testAltaUsuarioColaboradorOK() throws UsuarioYaExistenteException, RolInvalidoException {
		// abmUsuarios.altaUsuarioColaborador("usuario1", organizacion, listaDeRoles);
		abmUsuarios.altaUsuarioColaborador("usuario1", organizacion, listaDeRoles);
	}
	
	@Test
	public void testAltaUsuarioColaboradorNombreYaUtilizado() throws UsuarioYaExistenteException, RolInvalidoException  {
		abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion, listaDeRoles);
		assertThrows(UsuarioYaExistenteException.class, ()->{abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion, listaDeRoles);});
	}
}