package dominio.entidades.calculadorFiscal;

import com.google.gson.Gson;
import dominio.entidades.ETipoEmpresa;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CategorizadorFiscal {
    private Actividad[] actividades;

    public CategorizadorFiscal(){
        super();
        this.cargarActividades();
    }

    public ETipoEmpresa recategorizar( ETipoActividad tipoActividad, boolean esComisionista, double cantidadPersonal, double ventasPromedio){
        Actividad actividad = this.buscarActividad(tipoActividad);
        if (esComisionista){
            return actividad.obtenerTamanioEmpresaXPersonas(cantidadPersonal);
        } else {
            return actividad.obtenerTamanioEmpresaXVentas(ventasPromedio);
        }
    }

    private void cargarActividades () {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/calculadorFiscal/categorias"));
            actividades = new Gson().fromJson(bufferedReader, Actividad[].class);
        } catch (FileNotFoundException e) {
            actividades = new Actividad[]{};
        }
    }

    private Actividad buscarActividad(ETipoActividad tipoActividad) {
        for (int i = 0; i < actividades.length-1; ++i) {
            if (actividades[i].getTipo().equals(tipoActividad.toString())) {
                return actividades[i];
            }
        }
        return new Actividad();
    }

}
