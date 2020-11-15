package dominio.categorizacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dominio.entidades.Organizacion;
import dominio.operaciones.Operacion;
import dominio.categorizacion.exceptions.CategorizacionException;

import javax.persistence.*;

@Entity
// todo determinar la estrategia de herencia
public abstract class EntidadCategorizable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

//	@JoinTable(name = "entidad_categoria",
//			joinColumns = @JoinColumn(name = "operacion_id"),
//			inverseJoinColumns = {@JoinColumn(name = "nombre_categoria", referencedColumnName = "nombre" ),
//								@JoinColumn(name = "nombre_criterio", referencedColumnName = "nombre_criterio" )})
	@ManyToMany
	@JoinTable(name = "entidad_categoria")
	private List<Categoria> categoriasAsociadas;

	public EntidadCategorizable(){}

	public EntidadCategorizable(Operacion unaOperacion){
		categoriasAsociadas = new ArrayList<Categoria>();
	}

	public String getIdentificador() {
		return Integer.toString(this.id);
	}

	public abstract boolean esLaOperacion(String identificadorEntidadCategorizable);

	public abstract boolean esDeLaOrganizacion(Organizacion unaOrganizacion);

	public boolean esDeLaCategoria(Categoria unaCategoria) {
		return this.categoriasAsociadas.contains(unaCategoria) || 
				this.categoriasAsociadas.stream().anyMatch( categoria -> categoria.esDescendienteDe(unaCategoria));
	}
	
	public boolean tieneCategoriaDelCriterio(CriterioDeCategorizacion unCriterio) {
		return this.categoriasAsociadas.stream().anyMatch( categoria -> categoria.getCriterioDeCategorizacion().equals(unCriterio));
	}
	
	public Categoria categoriaDelCriterio(CriterioDeCategorizacion unCriterio) {
		return this.categoriasAsociadas.stream().filter(categoria -> categoria.getCriterioDeCategorizacion().equals(unCriterio)).findFirst().get();
	}
	
	public void asociarseACategoria(Categoria unaCategoria) throws CategorizacionException {
		this.categoriasAsociadas.add(unaCategoria);
		unaCategoria.seAsociaUnaEntidad();
	}
	
	public void desasociarseACategoria(Categoria unaCategoria) {
		this.categoriasAsociadas.remove(unaCategoria);
		unaCategoria.seDesasociaUnaEntidad();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
