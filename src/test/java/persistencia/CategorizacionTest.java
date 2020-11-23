package persistencia;

import datos.RepoOrganizaciones;
import datos.RepositorioCategorizacion;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.entidades.ETipoEmpresa;
import dominio.entidades.Empresa;
import dominio.entidades.Organizacion;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class CategorizacionTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager = null;



    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    @Test
    public void testGetCriteriosDeCategorizacion() {
        this.entityManager = entityManagerFactory.createEntityManager();

        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        ArrayList<CriterioDeCategorizacion> criterios = repositorioCategorizacion.getCriteriosDeCategorizacion();
    }

    @Test
    public void testCategorizarEgreso() throws CategorizacionException {
        this.entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        repositorioCategorizacion.asociarCategoriaAEntidadCategorizable("OE-80", "Categoria-1", "CriterioDePrueba-1");
        entityManager.getTransaction().commit();
    }

    @Test
    public void testCategorizarPresupuesto() throws CategorizacionException {
        this.entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        repositorioCategorizacion.asociarCategoriaAEntidadCategorizable("L-84-86", "Categoria-1", "CriterioDePrueba-1");

        entityManager.getTransaction().commit();
    }

    @Test
    public void testCategorizarPresupuesto2() throws CategorizacionException {
        this.entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        repositorioCategorizacion.asociarCategoriaAEntidadCategorizable("L-89-91", "Categoria-2", "CriterioDePrueba-1");

        entityManager.getTransaction().commit();
    }

    @Test
    public void testEntidadesDeLaCategoria(){
        this.entityManager = entityManagerFactory.createEntityManager();

        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(entityManager);

        ArrayList<Empresa> entidades = new ArrayList<>();

        Empresa entidad1 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "Empresa Origen 1", "Empresa 1", "20-40678950-3", "200", "Av.Libertador 801", false);
        entidades.add(entidad1);

        repoOrganizaciones.agregarOrganizacion("Organizacion3", entidades);

        Organizacion organizacion = repoOrganizaciones.buscarOrganizacion("Organizacion3");

        ArrayList<EntidadCategorizable> entidadesCategorizables = repositorioCategorizacion.filtrarEntidadesDeLaCategoria("Categoria-1", "CriterioDePrueba-1", organizacion);

        System.out.println("hola");
    }


}
