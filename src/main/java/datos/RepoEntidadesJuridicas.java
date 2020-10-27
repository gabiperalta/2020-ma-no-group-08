package datos;

import dominio.entidades.*;
import dominio.entidades.calculadorFiscal.ETipoActividad;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RepoEntidadesJuridicas {
    private EntityManager entityManager;

    public boolean tieneEntidad(String razonSocial) {
        try {
            buscarEntidadJuridica(razonSocial);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    private static class RepositorioEntidadesJuridicasHolder {
        static final RepoEntidadesJuridicas singleInstanceRepositorioEntidadesJuridicas = new RepoEntidadesJuridicas();
    }

    public static RepoEntidadesJuridicas getInstance() {
        return RepoEntidadesJuridicas.RepositorioEntidadesJuridicasHolder.singleInstanceRepositorioEntidadesJuridicas;
    }

    public RepoEntidadesJuridicas(EntityManager em) {
        entityManager = em;
    }

    public RepoEntidadesJuridicas() {
//        TODO: eliminar
    }


    public void agregarEntidadEmpresa(ETipoEmpresa tipo, Integer cantidadPersonal, ETipoActividad actividad, Double promedioVentas, String razonSocial,
                                      String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal, boolean esComisionista) {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            Empresa empresa = new Empresa(tipo, cantidadPersonal, actividad, promedioVentas, razonSocial, nombreFicticio, cuit, codigoIGJ, direccionPostal, esComisionista);
            entityManager.persist(empresa);
        }
    }

    public void agregarEntidadOSC(OSC osc) {
        if (this.buscarEntidadJuridica(osc.getRazonSocial()) == null) {
            entityManager.persist(osc);
        }
    }


    public void eliminarEntidadJuridica(String razonSocial) {
        EntidadJuridica entidadABorrar = buscarEntidadJuridica(razonSocial);
        if (entidadABorrar != null) {
            entityManager.remove(entidadABorrar);
        }
    }

    public EntidadJuridica buscarEntidadJuridica(String razonSocial) {
        return entityManager.find(Empresa.class, razonSocial);
    }

}
