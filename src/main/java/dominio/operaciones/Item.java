package dominio.operaciones;

public class Item {
	private int valor; 
	private ETipoItem tipo;
	private String descripcion;
	
	public Item(int i, ETipoItem articulo, String string) {
		this.valor = i;
		this.tipo = articulo;
		this.descripcion = string;
	}
	public int getValor() {
		return valor;
	}
}
