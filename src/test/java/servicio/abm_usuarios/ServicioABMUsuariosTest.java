package servicio.abm_usuarios;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dominio.entidades.Organizacion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

public class ServicioABMUsuariosTest {

	ServicioABMUsuarios abmUsuarios;
	Organizacion organizacion;
	
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException, UsuarioYaExistenteException {
		
		abmUsuarios = new ServicioABMUsuarios();

		organizacion = new Organizacion("Organizacion 1");
	}
	/*
	@Test
	public void testAltaUsuarioColaboradorOK() throws UsuarioYaExistenteException {
		abmUsuarios.altaUsuarioColaborador("usuario1", organizacion);
	}
	
	@Test
	public void testAltaUsuarioColaboradorNombreYaUtilizado() throws UsuarioYaExistenteException  {
		abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion);
		assertThrows(UsuarioYaExistenteException.class, ()->{abmUsuarios.altaUsuarioColaborador("usuarioRepetido", organizacion);});
	}
	*/
}