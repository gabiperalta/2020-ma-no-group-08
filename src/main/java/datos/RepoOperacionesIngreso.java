package datos;

import dominio.entidades.Organizacion;
import dominio.operaciones.OperacionIngreso;

import java.util.ArrayList;

public class RepoOperacionesIngreso {
    private static ArrayList<OperacionIngreso> ingresos;
    private static int ultimoIdentificador;

    public RepoOperacionesIngreso() {
        ingresos = new ArrayList<OperacionIngreso>();
        ultimoIdentificador = 1;
    }

    public ArrayList<OperacionIngreso> getIngresos() {
        return ingresos;
    }

    private static class RepositorioOperacionesIngresoHolder {
        static final RepoOperacionesIngreso singleInstanceRepositorioOperacionesIngreso = new RepoOperacionesIngreso();
    }

    public static RepoOperacionesIngreso getInstance() {
        return RepositorioOperacionesIngresoHolder.singleInstanceRepositorioOperacionesIngreso;
    }

    public void agregarIngreso(OperacionIngreso ingreso) throws Exception {
        String identificador = "OI-";
        ingreso.setIdentificador(identificador + ultimoIdentificador);
        ultimoIdentificador++;
        ingresos.add(ingreso);
    }

    public void eliminarIngreso(OperacionIngreso ingreso) {
        ingresos.remove(ingreso);
    }

    public OperacionIngreso buscarOperacionEgresoPorIdentificador(String identificadorEntidadCategorizable) {
        if (ingresos.stream().anyMatch(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable))) {
            return ingresos.stream().filter(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable)).findFirst().get();
        } else {
            return null;
        }
    }

    public OperacionIngreso buscarOperacionEgresoPorIdentificadorYOrganizacion(String identificadorEntidadCategorizable, Organizacion unaOrganizacion) {
        if (ingresos.stream().anyMatch(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable) && operacion.esDeLaOrganizacion(unaOrganizacion))) {
            return ingresos.stream().filter(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable) && operacion.esDeLaOrganizacion(unaOrganizacion)).findFirst().get();
        } else {
            return null;
        }
    }
}