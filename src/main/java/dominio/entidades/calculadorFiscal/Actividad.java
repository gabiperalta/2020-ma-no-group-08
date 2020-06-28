package dominio.entidades.calculadorFiscal;

import dominio.entidades.ETipoEmpresa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Actividad {
    private String tipo;
    private ArrayList<Integer> ventas;
    private ArrayList<Integer> personal;
    private List<ETipoEmpresa> tipos = Arrays.asList(ETipoEmpresa.MICRO,ETipoEmpresa.PEQUENIA,ETipoEmpresa.MEDIANA_T1,ETipoEmpresa.MEDIANA_T2);

    public String getTipo() {
        return tipo;
    }

    public ETipoEmpresa obtenerTamanioEmpresaXVentas(double ventasPromedio) {
        return obtenerPosicionQueCumpla(ventasPromedio, ventas);
    }

    public ETipoEmpresa obtenerTamanioEmpresaXPersonas(double cantidadPersonas) {
        return obtenerPosicionQueCumpla(cantidadPersonas, personal);
    }

    private ETipoEmpresa obtenerPosicionQueCumpla(double valorAEvaluar, ArrayList<Integer> arrayList) {
        for (int i = 0; i < arrayList.size()-1; ++i) {
            if (arrayList.get(i) > valorAEvaluar) {
                return tipos.get(i);
            }
        }

        return ETipoEmpresa.MEDIANA_T2;
    }
}
