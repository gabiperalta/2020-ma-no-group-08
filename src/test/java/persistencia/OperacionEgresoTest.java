package persistencia;

import datos.RepoOperacionesEgreso;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OperacionEgresoTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager = null;



    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testBuscarOperacionEgreso(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(entityManager);
        repoOperacionesEgreso.buscarOperacionEgresoPorIdenticadorOperacionEgreso("OE-80");
    }


}
