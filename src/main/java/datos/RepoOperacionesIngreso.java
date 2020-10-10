package datos;

import dominio.operaciones.OperacionIngreso;

import java.util.ArrayList;

public class RepoOperacionesIngreso {
	private static ArrayList<OperacionIngreso> ingresos;
	private static int ultimoIdentificador;	
	
	public ArrayList<OperacionIngreso> getIngresos() {
		return ingresos;
	}

	public RepoOperacionesIngreso() {
		ingresos = new ArrayList<OperacionIngreso>();
		ultimoIdentificador = 1;
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
        ultimoIdentificador ++ ;
    	ingresos.add(ingreso);
    }
    
    public void eliminarIngreso(OperacionIngreso ingreso) {
    	ingresos.remove(ingreso);
    }
    
    public OperacionIngreso buscarOperacionEgresoPorIdentificador(String identificadorEntidadCategorizable) {
    	return ingresos.stream().filter(operacion -> operacion.esLaOperacion(identificadorEntidadCategorizable)).findFirst().get();
    }

}
