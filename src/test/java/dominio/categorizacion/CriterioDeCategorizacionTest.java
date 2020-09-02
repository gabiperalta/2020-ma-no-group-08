package dominio.categorizacion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import dominio.categorizacion.exceptions.CategorizacionException;

public class CriterioDeCategorizacionTest {
	private CriterioDeCategorizacion criterioDePrueba;
	private EntidadCategorizable entidadDePrueba;
	
	@Before
	public void setUp() throws CategorizacionException {
		criterioDePrueba = new CriterioDeCategorizacion("CriterioDePrueba");
		criterioDePrueba.agregarCategoria("Categoria1");
		criterioDePrueba.agregarCategoria("Categoria1.1", "Categoria1");
		criterioDePrueba.agregarCategoria("Categoria2");
		criterioDePrueba.agregarCategoria("CategoriaAQuitarPrueba");
		criterioDePrueba.agregarCategoria("CategoriaPadreDePrueba");
		entidadDePrueba = new EntidadCategorizable(null);
	}
	
	@Test
	public void agregarCategoriaOK() throws CategorizacionException {
		criterioDePrueba.agregarCategoria("CategoriaDePrueba");
		assertTrue(criterioDePrueba.existeLaCategoria("CategoriaDePrueba"));
	}
	
	@Test
	public void agregarCategoriaRepetida() throws CategorizacionException {
		criterioDePrueba.agregarCategoria("CategoriaDePruebaRepetida");
		assertThrows("Esta categoria ya existe.", CategorizacionException.class, 
							()->{criterioDePrueba.agregarCategoria("CategoriaDePruebaRepetida");});
	}
	
	@Test
	public void agregarCategoriaConPadreOK() throws CategorizacionException {
		criterioDePrueba.agregarCategoria("CategoriaDePrueba", "CategoriaPadreDePrueba");
		assertTrue(criterioDePrueba.existeLaCategoria("CategoriaDePrueba") && 
					criterioDePrueba.buscarCategoria("CategoriaDePrueba").esDescendienteDe(criterioDePrueba.buscarCategoria("CategoriaPadreDePrueba")));
	}
	
	@Test
	public void agregarCategoriaConPadreInexistente() {
		assertThrows( NoSuchElementException.class, 
				()->{criterioDePrueba.agregarCategoria("CategoriaDePrueba", "CategoriaPadreInexistente");});
	}
	
	@Test
	public void quitarCategoriaOK() throws CategorizacionException {
		criterioDePrueba.quitarCategoria("CategoriaAQuitarPrueba");
		assertFalse(criterioDePrueba.existeLaCategoria("CategoriaAQuitarPrueba"));
	}
	
	@Test
	public void quitarCategoriaAsociadaAEntidad() {
		criterioDePrueba.buscarCategoria("CategoriaAQuitarPrueba").seAsociaUnaEntidad();
		assertThrows("Esta Categoria no puede ser eliminada porque hay Entidades asociadas.", CategorizacionException.class, 
				()->{criterioDePrueba.quitarCategoria("CategoriaAQuitarPrueba");});
	}
	
	@Test
	public void quitarCategoriaConCategoriasHijas() throws CategorizacionException {
		criterioDePrueba.agregarCategoria("CategoriaHijaPrueba", "CategoriaAQuitarPrueba");
		assertThrows("Esta Categoria no puede ser eliminada porque tiene categorias hijas.", CategorizacionException.class, 
				()->{criterioDePrueba.quitarCategoria("CategoriaAQuitarPrueba");});
	}
	
	@Test
	public void asociarCategoriaAEntidadCategorizableOK() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		Categoria unaCategoria = criterioDePrueba.buscarCategoria("Categoria1");
		assertTrue(entidadDePrueba.esDeLaCategoria(unaCategoria));
	}
	
	@Test
	public void asociarCategoriaAEntidadCategorizableSubCategoriaOK() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1.1", entidadDePrueba);
		Categoria unaCategoria = criterioDePrueba.buscarCategoria("Categoria1");
		Categoria otraCategoria = criterioDePrueba.buscarCategoria("Categoria1.1");
		assertTrue(entidadDePrueba.esDeLaCategoria(unaCategoria) && entidadDePrueba.esDeLaCategoria(otraCategoria));
	}
	
	@Test
	public void asociarCategoriaAEntidadCategorizableCategoriaInvalida() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		assertThrows("La Categoria que quiere asociarse no es valida con una de las que la Entidad ya tenia.", CategorizacionException.class, 
				()->{criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria2", entidadDePrueba);});
	}
	
	@Test
	public void asociarCategoriaAEntidadCategorizableCategoriaRepetida() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		assertThrows("La Entidad ya esta asociada a esa Categoria.", CategorizacionException.class, 
				()->{criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);});
	}
	
	@Test
	public void asociarCategoriaAEntidadCategorizableSubCategoriaYaAsociada() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1.1", entidadDePrueba);
		assertThrows("La Entidad ya esta asociada a esa Categoria.", CategorizacionException.class, 
				()->{criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);});
	}
	
	@Test
	public void desasociarCategoriaAEntidadCategorizableOK() throws CategorizacionException {
		criterioDePrueba.asociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		criterioDePrueba.desasociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);
		Categoria unaCategoria = criterioDePrueba.buscarCategoria("Categoria1");
		assertFalse(entidadDePrueba.esDeLaCategoria(unaCategoria));
	}
	
	@Test
	public void desasociarCategoriaAEntidadCategorizableCategoriaNoAsociada() throws CategorizacionException {
		assertThrows("Esta Entidad no esta asociada a esa Categoria.", CategorizacionException.class, 
				()->{criterioDePrueba.desasociarCategoriaAEntidadCategorizable("Categoria1", entidadDePrueba);});
	}
}
