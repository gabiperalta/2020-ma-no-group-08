package servicio.abOperaciones;

import datos.RepoOperacionesEgreso;
import datos.RepoOperacionesIngreso;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServicioABOperaciones {

    EntityManager entityManager;

    public ServicioABOperaciones(EntityManager unEntityManager){
        this.entityManager = unEntityManager;
    }

    public ArrayList<OperacionIngreso> listarIngresosPorOrg(Organizacion organizacion){
        return new ArrayList<OperacionIngreso>(this.listarIngresos().stream().
                filter(ingreso -> ingreso.esDeLaOrganizacion(organizacion)).collect(Collectors.toList()));
    }

    public ArrayList<OperacionEgreso> listarOperaciones(){
        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(this.getEntityManager());
        return repoOperacionesEgreso.getOperacionesEgreso();
    }

    public ArrayList<OperacionIngreso> listarIngresos(){
        RepoOperacionesIngreso repoIngresos = new RepoOperacionesIngreso(this.getEntityManager());
        return repoIngresos.getIngresos();
    }

    public ArrayList<OperacionEgreso> listarOperaciones(Organizacion unaOrganizacion){
        return new ArrayList<OperacionEgreso>(this.listarOperaciones().stream().
                filter(egreso -> egreso.esDeLaOrganizacion(unaOrganizacion)).collect(Collectors.toList()));
    }

    public OperacionEgreso buscarEgreso(String identificadorEgreso){
        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(getEntityManager());
        return repoOperacionesEgreso.buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorEgreso);
    }

    private EntityManager getEntityManager(){
        return this.entityManager;
    }
}
