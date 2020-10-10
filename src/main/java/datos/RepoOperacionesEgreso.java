package datos;

import dominio.entidades.Organizacion;
import dominio.operaciones.OperacionEgreso;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepoOperacionesEgreso {

    private static ArrayList<OperacionEgreso> operacionesEgreso;
    private static int ultimoIdentificador;

    private static class RepositorioOperacionesEgresoHolder {
        static final RepoOperacionesEgreso singleInstanceRepositorioOperacionesEgreso = new RepoOperacionesEgreso();
    }

    public static RepoOperacionesEgreso getInstance() {
        return RepositorioOperacionesEgresoHolder.singleInstanceRepositorioOperacionesEgreso;
    }

    public RepoOperacionesEgreso() {
        operacionesEgreso = new ArrayList<OperacionEgreso>();
        ultimoIdentificador = 1 ;
    }

    public void agregarOperacionEgreso(OperacionEgreso operacionEgreso) throws Exception {
        String identificador = "OE-";
        operacionEgreso.setIdentificador(identificador + ultimoIdentificador);
        ultimoIdentificador ++ ;
        operacionesEgreso.add(operacionEgreso);
    }

    public void eliminarOperacionEgreso(OperacionEgreso operacionEgreso) {
        buscarOperacionEgreso(operacionEgreso);
        operacionesEgreso.remove(operacionEgreso);
    }

    public void eliminarOperacionEgresoPorIdentificador(String identificadorOperacionEgreso) {
        OperacionEgreso egresoAEliminar = buscarOperacionEgresoPorIdenticadorOperacionEgreso(identificadorOperacionEgreso);
        operacionesEgreso.remove(egresoAEliminar);
    }

    public static OperacionEgreso buscarOperacionEgreso(OperacionEgreso operacionEgreso){
        return operacionesEgreso.stream().filter(operacion -> operacion.equals(operacionEgreso)).findFirst().get(); // si no encuentra nada, tira NoSuchElementException
    }

    public OperacionEgreso buscarOperacionEgresoPorIdenticadorOperacionEgreso(String identifacdorOperacionEgreso){
        return operacionesEgreso.stream().filter(operacion -> operacion.getIdentificador().equals(identifacdorOperacionEgreso)).findFirst().get(); // si no encuentra nada, tira NoSuchElementException
    }

    public ArrayList<OperacionEgreso> getOperacionesEgreso(){
        return operacionesEgreso;
    }

    public ArrayList<OperacionEgreso> getOperacionesEgresoDeLaOrganizacion(Organizacion unaOrganizacion){
        return new ArrayList< OperacionEgreso >(this.getOperacionesEgreso().stream().filter(egreso -> egreso.esDeLaOrganizacion(unaOrganizacion)).collect(Collectors.toList()));
    }

    public OperacionEgreso buscarOperacionEgresoPorIdentificador(String identificadorEntidadCategorizable) {
        return operacionesEgreso.stream().filter(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable)).findFirst().get();
    }
}

