package dominio.licitacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.categorizacion.EntidadCategorizable;
import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.Operacion;
import dominio.operaciones.OperacionEgreso;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@Entity
public class Presupuesto extends EntidadCategorizable {

	@Id @GeneratedValue
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	private EntidadOperacion proveedor;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Item> items;
	private double montoTotal;
	private boolean esValido;

	@OneToOne
	private EntidadOperacion entidadOrigen;

	public Presupuesto(){

	}

	public Presupuesto(EntidadOperacion proveedor, ArrayList<Item> unosItems){
		super();
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

	public List<Item> getItems() {
		return items;
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
		return "P-" + super.getIdentificador(); // TODO revisar
	}

	public void setEntidadOrigen(EntidadOperacion entidadOrigen){
		this.entidadOrigen = entidadOrigen;
	}

	@Override
	public boolean esLaOperacion(String identificadorEntidadCategorizable) {
		return this.getIdentificador().contentEquals(identificadorEntidadCategorizable);
	}

//	public boolean esIngreso() {
//		return false;
//	}

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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public boolean esCorrespondiente(OperacionEgreso operacion) {
		return this.items.stream().allMatch(i->i.esCorrespondiente(operacion) && this.proveedor.correspondeEntidad(operacion.getEntidadOrigen()));
	}
}
