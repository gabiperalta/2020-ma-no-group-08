package persistencia;

import datos.RepoEntidadesJuridicas;
import datos.RepoOperacionesEgreso;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntidadJuridicaTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager = null;



    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testBuscarOperacionEgreso(){
        this.entityManager = entityManagerFactory.createEntityManager();
        RepoEntidadesJuridicas repoEntidadesJuridicas = new RepoEntidadesJuridicas(entityManager);
        repoEntidadesJuridicas.buscarEntidadJuridica("Empresa 1");
    }

}
