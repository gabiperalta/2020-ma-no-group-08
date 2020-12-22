package persistencia;

import datos.RepoOrganizaciones;
import datos.RepositorioUsuarios;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.entidades.Organizacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class CuentaUsuarioTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager = null;



    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testAgregarUsuarioEstandar(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(entityManager);
        Organizacion organizacion = new Organizacion("OrganizacionDePrueba", null);
        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
        listaDeRolesCliente.add("ROL_ESTANDAR");

        repositorioUsuarios.agregarUsuarioEstandar("usuarioDePrueba", organizacion, listaDeRolesCliente, "1234");
    }

    @Test
    public void testExisteElUsuario(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(entityManager);
//        Organizacion organizacion = new Organizacion("OrganizacionDePrueba", null);
//        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
//        listaDeRolesCliente.add("ROL_ESTANDAR");
//        repositorioUsuarios.agregarUsuarioEstandar("usuarioExistente", organizacion, listaDeRolesCliente, "1234");

        Assert.assertTrue(repositorioUsuarios.existeElUsuario("UsuarioWeb1"));
    }

    @Test
    public void testBuscarUsuario(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(entityManager);
//        Organizacion organizacion = new Organizacion("OrganizacionDePrueba", null);
//        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
//        listaDeRolesCliente.add("ROL_ESTANDAR");
//        repositorioUsuarios.agregarUsuarioEstandar("usuarioABuscar", organizacion, listaDeRolesCliente, "1234");

        CuentaUsuario usuario = repositorioUsuarios.buscarUsuario("UsuarioWeb1");

        System.out.println("Nombre usuario buscado: " + usuario.getUserName());
    }

    @Test
    public void testEliminarUsuarioEstandar() throws CredencialesNoValidasException {
        this.entityManager = entityManagerFactory.createEntityManager();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(entityManager);

        entityManager.getTransaction().begin();

        Organizacion organizacion = new RepoOrganizaciones(entityManager).buscarOrganizacion("Organizacion1");
        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
        listaDeRolesCliente.add("ROL_ESTANDAR");

        repositorioUsuarios.agregarUsuarioEstandar("usuarioAEliminar", organizacion, listaDeRolesCliente, "1234");
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        repositorioUsuarios.eliminarUsuarioEstandar("usuarioAEliminar", "1234");
        entityManager.getTransaction().commit();

        Assert.assertTrue(!repositorioUsuarios.existeElUsuario("usuarioAEliminar"));
    }

    @Test
    public void testBuscarRol(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(entityManager);

        Rol rol = repositorioUsuarios.buscarRol("ROL_ESTANDAR");

        System.out.println("Nombre rol buscado: " + rol.getNombre());

        for (Privilegio privilegio : rol.getPrivilegios()) {
            System.out.println("Nombre privilegio: " + privilegio.getNombre());
        }
    }


}
