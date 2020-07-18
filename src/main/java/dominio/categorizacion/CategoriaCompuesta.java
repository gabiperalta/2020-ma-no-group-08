/*package dominio.categorizacion;

import java.util.ArrayList;

public class CategoriaCompuesta extends Categoria{
	private String nombre;
	private ArrayList<Categoria> categorizaciones;
	private ArrayList<EntidadCategorizable> entidades;
	
	public CategoriaCompuesta(String unNombre, ArrayList<Categoria> subCategorias) {
		nombre = unNombre;
		categorizaciones = subCategorias;
		entidades = new ArrayList<EntidadCategorizable>();
	}
	
	public void asociar(EntidadCategorizable entidad, ArrayList<String> subCategorias) {
		if(subCategorias.size()>0) {
			this.buscarSubCategoria(subCategorias).asociar(entidad, subCategorias);;
		}
		if(!this.entidades.contains(entidad)) {
			this.entidades.add(entidad);
		}
		else {
			throw new Exception("La entidad ya esta asociada a esta categoria");
		}
	}
	private Categoria buscarSubCategoria(String nombreCategoria) {
		return null;
	}


	@Override
	public EntidadCategorizable buscarSubCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}
}*/