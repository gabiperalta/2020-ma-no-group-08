package dominio.categorizacion;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {
	@Id
	@GeneratedValue
	private int id;

	private String nombre;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Categoria categoriaPadre;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private CriterioDeCategorizacion criterioDeCategorizacion;

	private int cantidadEntidadesCategorizablesAsociadas;

	public Categoria() {}
	
	public Categoria(String nombreCategoria, CriterioDeCategorizacion criterioDeCategorizacion, Categoria categoriaPadre){
		this.nombre = nombreCategoria;
		this.categoriaPadre = categoriaPadre;
		this.criterioDeCategorizacion = criterioDeCategorizacion;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public Categoria getCategoriaPadre() {
		return this.categoriaPadre;
	}
	
	public CriterioDeCategorizacion getCriterioDeCategorizacion() {
		return criterioDeCategorizacion;
	}
	
	public boolean esDelCriterio(String nombreCriterioDeCategorizacion) {
		return this.criterioDeCategorizacion.getNombre().contentEquals(nombreCriterioDeCategorizacion);
	}
	
	public boolean esDescendienteDe(Categoria unaCategoriaPadre) { // En caso de que no funcione, debo poner un if porque aparentemente no tiene lazy evaluation
		if(this.categoriaPadre != null)
			return this.categoriaPadre.equals(unaCategoriaPadre) || this.categoriaPadre.esDescendienteDe(unaCategoriaPadre);
		else
			return false;
	}
	
	public boolean tieneEntidadesAsociadas(){
		return this.cantidadEntidadesCategorizablesAsociadas > 0;
	}
	
	public void desasociarseDelPadre(){
			this.categoriaPadre = null;
	}
	
	public void seAsociaUnaEntidad() {
		this.cantidadEntidadesCategorizablesAsociadas ++;
	}
	
	public void seDesasociaUnaEntidad() {
		this.cantidadEntidadesCategorizablesAsociadas --;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCategoriaPadre(Categoria categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public void setCriterioDeCategorizacion(CriterioDeCategorizacion criterioDeCategorizacion) {
		this.criterioDeCategorizacion = criterioDeCategorizacion;
	}

	public int getCantidadEntidadesCategorizablesAsociadas() {
		return cantidadEntidadesCategorizablesAsociadas;
	}

	public void setCantidadEntidadesCategorizablesAsociadas(int cantidadEntidadesCategorizablesAsociadas) {
		this.cantidadEntidadesCategorizablesAsociadas = cantidadEntidadesCategorizablesAsociadas;
	}
}
