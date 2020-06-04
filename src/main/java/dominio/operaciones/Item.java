package dominio.operaciones;

public class Item {
	private double valor; 
	private ETipoItem tipo;
	private String descripcion;
	
	
	public Item(int i, ETipoItem articulo, String string) {
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public ETipoItem getTipo() {
		return tipo;
	}
	public void setTipo(ETipoItem tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
