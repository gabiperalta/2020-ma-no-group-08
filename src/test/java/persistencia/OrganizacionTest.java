package persistencia;

import datos.RepoOrganizaciones;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.entidades.Organizacion;
import dominio.notificador_suscriptores.Mensaje;
import dominio.notificador_suscriptores.bandeja_de_mensajes.BandejaDeMensajes;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OrganizacionTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager = null;



    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testCrearOrganizacion(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(entityManager);

        repoOrganizaciones.agregarOrganizacion("unaOrganizacionDePrueba", null);
    }

    @Test
    public void testBuscarOrganizacion(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(entityManager);

        repoOrganizaciones.agregarOrganizacion("unaOrganizacionDePrueba", null);

        Organizacion unaOrganizacion = repoOrganizaciones.buscarOrganizacion("unaOrganizacionDePrueba");

        System.out.println("Nombre irganizacion buscada: " + unaOrganizacion.getNombre());
    }

    @Test
    public void testExisteLaOrganizacion(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(entityManager);

        repoOrganizaciones.agregarOrganizacion("unaOrganizacionExistente", null);

        Assert.assertTrue(repoOrganizaciones.existeLaOrganizacion("unaOrganizacionExistente"));
    }

    @Test
    public void testEliminarOrganizacion(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(entityManager);

        repoOrganizaciones.agregarOrganizacion("unaOrganizacionAEliminar", null);

        repoOrganizaciones.eliminarOrganizacion("unaOrganizacionAEliminar");

        Assert.assertTrue(!repoOrganizaciones.existeLaOrganizacion("unaOrganizacionAEliminar"));
    }

}
