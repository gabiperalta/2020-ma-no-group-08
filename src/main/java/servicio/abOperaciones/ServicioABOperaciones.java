package servicio.abOperaciones;

import datos.RepoOperacionesEgreso;
import datos.RepoOperacionesIngreso;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.MedioDePago;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class ServicioABOperaciones {

//    public void altaOperacion(ArrayList<Item> items, MedioDePago medioDePago, DocumentoComercial documento, Date fecha, double valorOperacion, EntidadOperacion entidadOrigen, EntidadOperacion entidadDestino) throws Exception{
//        OperacionEgresoBuilder operacionEgresoBuilder = new OperacionEgresoBuilder();
//        operacionEgresoBuilder.agregarItems(items)
//                .agregarMedioDePago(medioDePago)
//                .agregarDocComercial(documento)
//                .agregarFecha(fecha)
//                .agregarEntidadOrigen(entidadOrigen)
//                .agregarEntidadDestino(entidadDestino);
//
//        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(getEntityManager());
//        repoOperacionesEgreso.agregarOperacionEgreso(operacionEgresoBuilder.build());
//    }


    public ArrayList<OperacionIngreso> listarIngresosPorOrg(Organizacion organizacion){
        return new ArrayList<OperacionIngreso>(this.listarIngresos().stream().
                filter(ingreso -> ingreso.esDeLaOrganizacion(organizacion)).collect(Collectors.toList()));    }

//    public void bajaOperacion(OperacionEgreso operacionEgreso){
//        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(getEntityManager());
//        repoOperacionesEgreso.eliminarOperacionEgreso(operacionEgreso);
//    }

    public ArrayList<OperacionEgreso> listarOperaciones(){
        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(getEntityManager());
        return repoOperacionesEgreso.getOperacionesEgreso();
    }

    public ArrayList<OperacionIngreso> listarIngresos(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("db").createEntityManager();
        RepoOperacionesIngreso repoIngresos = new RepoOperacionesIngreso(entityManager);
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

    private EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("db").createEntityManager();
    }
}
