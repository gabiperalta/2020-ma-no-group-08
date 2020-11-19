package datos;

import auditoria.RepoAuditorias;
import dev.morphia.Datastore;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.entidades.Organizacion;
import dominio.operaciones.DocumentoComercial;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.medioDePago.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class RepoOperacionesEgreso {
    private EntityManager entityManager;

    public RepoOperacionesEgreso(EntityManager em){
        entityManager = em;
    }

    public void agregarOperacionEgreso(OperacionEgreso operacionEgreso, String nombreUsuario, Datastore datastore) throws Exception {
        if (!existeEgreso(operacionEgreso)) {
            cargarEntidadOperacion(operacionEgreso.getEntidadOrigen());
            cargarEntidadOperacion(operacionEgreso.getEntidadDestino());
            entityManager.persist(operacionEgreso);
            new RepoAuditorias(datastore).registrarAlta(operacionEgreso.getIdentificador(), nombreUsuario);
        }
    }

    private void cargarEntidadOperacion(EntidadOperacion entidadOperacion) {
        if (!entityManager.contains(entidadOperacion)) {
            entityManager.persist(entidadOperacion);
        }
    }

    private boolean existeEgreso(OperacionEgreso operacionEgreso) {
        return entityManager.contains(operacionEgreso);
    }

    public void eliminarOperacionEgreso(OperacionEgreso operacionEgreso, String nombreUsuario, Datastore datastore) {
        entityManager.remove(operacionEgreso);
        new RepoAuditorias(datastore).registrarBaja(operacionEgreso.getIdentificador(), nombreUsuario);
    }

    public void eliminarOperacionEgresoPorIdentificador(String identificadorOperacionEgreso, String nombreUsuario, Datastore datastore) {
        OperacionEgreso egresoAEliminar = buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorOperacionEgreso);
        entityManager.remove(egresoAEliminar);
        new RepoAuditorias(datastore).registrarBaja(egresoAEliminar.getIdentificador(), nombreUsuario);
    }

    public OperacionEgreso buscarOperacionEgresoPorIdenticadorOperacionEgreso(String identifacdorOperacionEgreso){
        String identificadorAcotado = identifacdorOperacionEgreso.substring(3);
        int identificador = Integer.parseInt(identificadorAcotado);
        return entityManager.find(OperacionEgreso.class, identificador);
    }

    public ArrayList<OperacionEgreso> getOperacionesEgreso(){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OperacionEgreso> consulta = cb.createQuery(OperacionEgreso.class);
        Root<OperacionEgreso> operacionesEgresoC = consulta.from(OperacionEgreso.class);
        return new ArrayList<>(this.entityManager.createQuery(consulta.select(operacionesEgresoC)).getResultList());
    }

    public void modificarEgreso(OperacionEgreso egreso, MedioDePago medioDePagoFinal, DocumentoComercial documento, Date fecha, EntidadOperacion entidadOrigen, EntidadOperacion entidadDestino, String nombreUsuario, Datastore datastore){
        egreso.modificarse(medioDePagoFinal , documento, fecha, entidadOrigen, entidadDestino);
        new RepoAuditorias(datastore).registrarModificacion(egreso.getIdentificador(), nombreUsuario);
    }
}

