package persistencia;

import datos.RepoOperacionesIngreso;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OperacionIngresoTest {

    static private EntityManagerFactory entityManagerFactory;
    static private EntityManager entityManager = null;



    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testBuscarOperacionIngreso(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOperacionesIngreso repoOperacionesIngreso = new RepoOperacionesIngreso(entityManager);

        repoOperacionesIngreso.buscarOperacionIngresoPorIdentificador("OI-49");
    }

}
