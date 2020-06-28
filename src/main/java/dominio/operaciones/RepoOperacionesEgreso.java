package dominio.operaciones;

import java.util.ArrayList;

public class RepoOperacionesEgreso {

    private static ArrayList<OperacionEgreso> operacionesEgreso;

    private static class RepositorioOperacionesEgresoHolder {
        static final RepoOperacionesEgreso singleInstanceRepositorioOperacionesEgreso = new RepoOperacionesEgreso();
    }

    public static RepoOperacionesEgreso getInstance() {
        return RepositorioOperacionesEgresoHolder.singleInstanceRepositorioOperacionesEgreso;
    }

    public RepoOperacionesEgreso() {
        operacionesEgreso = new ArrayList<OperacionEgreso>();
    }

    public void agregarOperacionEgreso(OperacionEgreso operacionEgreso) {
        operacionesEgreso.add(operacionEgreso);
    }

    public void eliminarOperacionEgreso(OperacionEgreso operacionEgreso) {
        OperacionEgreso operacionABorrar = buscarOperacionEgreso(operacionEgreso);
        operacionesEgreso.remove(operacionEgreso);
    }

    public static OperacionEgreso buscarOperacionEgreso(OperacionEgreso operacionEgreso){
        return operacionesEgreso.stream().filter(operacion -> operacion.equals(operacionEgreso)).findFirst().get(); // si no encuentra nada, tira NoSuchElementException
    }

    public ArrayList<OperacionEgreso> getOperacionesEgreso(){
        return operacionesEgreso;
    }
}
