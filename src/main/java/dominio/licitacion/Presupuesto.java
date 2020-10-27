package dominio.licitacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.Operacion;
import dominio.operaciones.OperacionEgreso;

import javax.persistence.*;

@Entity
public class Presupuesto extends Operacion {
	@OneToOne
	private EntidadOperacion proveedor;

	@OneToMany
	@JoinColumn(name = "identificador")
	private List<Item> items;
	private double montoTotal;
	private boolean esValido;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int identificador;
	@Transient
	private EntidadOperacion entidadOrigen;

	public Presupuesto(){

	}

	public Presupuesto(EntidadOperacion proveedor, ArrayList<Item> unosItems){
		this.proveedor = proveedor;
		items = new ArrayList<Item>();
		this.esValido = true;

		unosItems.forEach(i->{
			try {
				this.agregarItem(i);
			} catch (Exception e) {
				e.getMessage();
				this.esValido = false;
			}
		});

		montoTotal = items.stream().map(item -> item.getValor()).reduce(0, (a, b) -> a + b);

	}

	public ArrayList<Item> getItems() {
		return (ArrayList<Item>) items;
	}

	private void agregarItem(Item item) throws Exception {
		if(this.items.stream().anyMatch(i->i.getDescripcion() == item.getDescripcion())) {
			throw new Exception("No se puede agregar item");
		}
		this.items.add(item);
	}
	public double getMontoTotal() {
		return this.montoTotal;
	}

	public String getIdentificador(){
		return "P-" + identificador; // TODO revisar
	}

	public void setEntidadOrigen(EntidadOperacion entidadOrigen){
		this.entidadOrigen = entidadOrigen;
	}

	@Override
	public boolean esLaOperacion(String identificadorEntidadCategorizable) {
		//return this.identificador.equals(identificadorEntidadCategorizable);
		return true; //TODO revisar
	}

	@Override
	public boolean esIngreso() {
		return false;
	}

	@Override
	public Date getFecha() {
		return null;
	}

	@Override
	public boolean esDeLaOrganizacion(Organizacion unaOrganizacion) {
		return unaOrganizacion.existeLaEntidad(this.entidadOrigen.getNombre());
	}

	public EntidadOperacion getProveedor() {
		return proveedor;
	}
	public void setProveedor(EntidadOperacion proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "[ montoTotal=" + getMontoTotal() + "]";
	}

	public boolean esValido(OperacionEgreso operacion) {
		return this.esValido && this.items.size() == operacion.getItems().size() && this.items.stream().allMatch(i->i.esValido(operacion));
	}

	public boolean esCorrespondiente(OperacionEgreso operacion) {
		return this.items.stream().allMatch(i->i.esCorrespondiente(operacion) && this.proveedor.correspondeEntidad(operacion.getEntidadOrigen()));
	}
}
