package datos;

import dominio.entidades.*;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import dominio.licitacion.Licitacion;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
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

    public RepoEntidadesJuridicas(EntityManager em) {
        entityManager = em;
    }

    public void agregarEntidadEmpresa(ETipoEmpresa tipo, Integer cantidadPersonal, ETipoActividad actividad, Double promedioVentas, String razonSocial,
                                      String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal, boolean esComisionista) {

        Empresa empresa = new Empresa(tipo, cantidadPersonal, actividad, promedioVentas, razonSocial, nombreFicticio, cuit, codigoIGJ, direccionPostal, esComisionista);
        if (!entityManager.contains(empresa)) {
            entityManager.persist(empresa);
        }
    }

    public void agregarEntidadOSC(OSC osc) {
        if (this.buscarEntidadJuridica(osc.getRazonSocial()) == null) {
            entityManager.persist(osc);
        }
    }


    public void eliminarEntidadJuridica(String razonSocial) {
        Empresa entidadABorrar = buscarEntidadJuridica(razonSocial);
        if (entidadABorrar != null) {
            entityManager.remove(entidadABorrar);
        }
    }

    public Empresa buscarEntidadJuridica(String razonSocial) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Empresa> consulta = cb.createQuery(Empresa.class);
        Root<Empresa> empresas = consulta.from(Empresa.class);
        Predicate condicion = cb.equal(empresas.get("razonSocial"), razonSocial);
        CriteriaQuery<Empresa> where = consulta.select(empresas).where(condicion);

        List<Empresa> listaEmpresas = this.entityManager.createQuery(where).getResultList();

        if(listaEmpresas.size() > 0)
            return listaEmpresas.get(0);
        else
            return null;

    }

}
