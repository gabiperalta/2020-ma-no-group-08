package componenteVinculador.criterio;

import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.criterio.vinculacion.OrdenFechaPrimerEgreso;
import componenteVinculador.criterio.vinculacion.OrdenValorPrimerEgreso;
import componenteVinculador.criterio.vinculacion.OrdenValorPrimerIngreso;

public class GeneradorCriterio {
    public static CriterioVinculacion generarCriterio(String tipoString, Object parametro) throws Exception {
        switch (tipoString) {
            case "Fecha_Egreso":
                return new OrdenFechaPrimerEgreso(parametro);
            case "Valor_Egreso":
                return new OrdenValorPrimerEgreso(parametro);
            case "Valor_Ingreso":
                return new OrdenValorPrimerIngreso(parametro);
            default:
                throw new Exception("Criterio invalido");
        }
    }
}
