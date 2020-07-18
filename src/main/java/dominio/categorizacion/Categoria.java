package dominio.categorizacion;

import java.util.ArrayList;

public abstract class Categoria {
	
	public abstract void asociar(EntidadCategorizable entidad, ArrayList<String> subCategorias) throws Exception;
	
}
