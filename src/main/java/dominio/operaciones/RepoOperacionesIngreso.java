package dominio.operaciones;

import java.util.ArrayList;

public class RepoOperacionesIngreso {
	private static ArrayList<OperacionIngreso> ingresos;
	
	
	
	public ArrayList<OperacionIngreso> getIngresos() {
		return ingresos;
	}

	public RepoOperacionesIngreso() {
		ingresos = new ArrayList<OperacionIngreso>();
	}
	
    private static class RepositorioOperacionesIngresoHolder {
        static final RepoOperacionesIngreso singleInstanceRepositorioOperacionesIngreso = new RepoOperacionesIngreso();
    }

    public static RepoOperacionesIngreso getInstance() {
        return RepositorioOperacionesIngresoHolder.singleInstanceRepositorioOperacionesIngreso;
    }
    
    public void agregarIngreso(OperacionIngreso ingreso) {
    	ingresos.add(ingreso);
    }
    
    public void eliminarIngreso(OperacionIngreso ingreso) {
    	ingresos.remove(ingreso);
    }

}
