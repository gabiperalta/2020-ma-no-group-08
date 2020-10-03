package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.vinculable.OperacionVinculable;
import dominio.operaciones.Operacion;

import java.util.ArrayList;

public class OrdenValorPrimerIngreso extends CriterioVinculacion {

    public OrdenValorPrimerIngreso(Object parametroCondicion) {
        super(parametroCondicion);
    }
//
//    @Override
//    public ArrayList<Vinculacion> ejecutar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
//        ordenar(ingresos,egresos, getCriterioOrden());
//        return vincular(egresos,ingresos);
//    }

    @Override
    protected CriterioOrden getCriterioOrden() {
        return new CriterioOrdenValor();
    }

    @Override
    protected ArrayList<Vinculacion> vincular(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        ArrayList<Vinculacion> vinculaciones = new ArrayList<>();
        ArrayList<OperacionVinculable> egresosDescartados = new ArrayList<>();

        for (OperacionVinculable ingreso : ingresos) {
            for (OperacionVinculable egreso : egresos) {
                Vinculacion resultado = this.buscaOCreaResultadoNuevo(ingreso,vinculaciones);

                if(resultado.sePuedeVincularEgreso(egreso) && cumpleCondicion(ingreso.getFecha(), egreso.getFecha()) && !egresosDescartados.contains(egreso)){
                    resultado.vincularNuevoEgreso(egreso);
                    egresosDescartados.add(egreso);
                    agregarResultadoSiEsNecesario(resultado, vinculaciones);
                }
            }
        }

        return vinculaciones;
    }
    @Override
    public ETipoCriterioVinculacion getTipoCriterio() {
        return ETipoCriterioVinculacion.VALOR_INGRESO;
    }
}
