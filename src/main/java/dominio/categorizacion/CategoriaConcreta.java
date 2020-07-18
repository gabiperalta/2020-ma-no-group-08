package dominio.categorizacion;

import java.util.ArrayList;

public class CategoriaConcreta extends Categoria{
	private String nombre;
	private ArrayList<EntidadCategorizable> entidades;
	
	CategoriaConcreta(String unNombre){
		nombre = unNombre;
		entidades = new ArrayList<EntidadCategorizable>();				
	}
	
	public void asociar(EntidadCategorizable entidad, ArrayList<String> subCategorias) throws Exception{
		if(subCategorias.size() > 0) {
			throw new Exception();
		}
		if(!this.entidades.contains(entidad)) {
			this.entidades.add(entidad);
		}
		else {
			throw new Exception("La entidad ya esta asociada a esta categoria");
		}
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public ArrayList<EntidadCategorizable> getEntidades(){
		return this.entidades;
	}
}
