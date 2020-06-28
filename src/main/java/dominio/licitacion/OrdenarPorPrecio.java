package dominio.licitacion;

import java.util.Comparator;

public class OrdenarPorPrecio implements Comparator<Presupuesto>{
	public int compare(Presupuesto p1, Presupuesto p2) {
		return (int) (p1.getMontoTotal() - p2.getMontoTotal());
	}
}
