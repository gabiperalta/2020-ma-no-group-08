package dominio.entidades.calculadorFiscal;

import com.google.gson.Gson;
import dominio.entidades.ETipoEmpresa;
import dominio.entidades.Empresa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CategorizadorFiscal {
    private Actividad[] actividades;

    public CategorizadorFiscal(){
        super();
        this.cargarActividades();
    }

    public ETipoEmpresa categorizar(Empresa empresa){
        Actividad actividad = this.buscarActividad(empresa.getActividad());
        if (empresa.isEsComisionista()){
            return actividad.obtenerTamanioEmpresaXPersonas(empresa.getCantidadPersonal());
        } else {
            return actividad.obtenerTamanioEmpresaXVentas(empresa.getPromedioVentas());
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
