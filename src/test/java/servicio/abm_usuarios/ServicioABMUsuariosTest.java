package servicio.abm_usuarios;

import static org.junit.Assert.*;

import java.util.ArrayList;

//import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import dominio.entidades.ETipoEmpresa;
import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import org.junit.Before;
import org.junit.Test;

//import dominio.cuentasUsuarios.Roles.Rol;
//import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import seguridad.sesion.exceptions.CredencialesNoValidasException;
import seguridad.sesion.exceptions.PermisoDenegadoException;
import temporal.seguridad.repositorioUsuarios.exceptions.RolInvalidoException;
import temporal.seguridad.repositorioUsuarios.exceptions.UsuarioYaExistenteException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServicioABMUsuariosTest {

	ServicioABMUsuarios abmUsuarios;
	Organizacion organizacion;
	EntidadOperacion entidadOperacion;
	ArrayList<String> listaDeRoles;
			
	@Before
	public void init() throws CredencialesNoValidasException, PermisoDenegadoException, UsuarioYaExistenteException {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
		EntityManager em = entityManagerFactory.createEntityManager();


		abmUsuarios = new ServicioABMUsuarios(em);

		ArrayList<Empresa> entidades = new ArrayList<>();

		entidades.add(new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "Empresa 1", "Empresa 1", "20-40678950-3", "200", "Av.Libertador 801",false));

		organizacion = new Organizacion("Organizacion 1",entidades);
		
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