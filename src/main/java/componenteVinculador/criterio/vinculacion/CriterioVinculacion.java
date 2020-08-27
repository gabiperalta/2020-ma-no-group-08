package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;

import java.util.ArrayList;
import java.util.Date;

public class CriterioVinculacion {
    private ArrayList<ResultadoVinculado> resultados;
    private Object parametro;

    public CriterioVinculacion(Object parametroCondicion) {

        resultados = new ArrayList<>();
        parametro = parametroCondicion;
    }

    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos) {
        ordenar(ingresos,egresos, getCriterioOrden());
        vincular(ingresos,egresos);
    }

    protected void vincular(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        for (OperacionVinculable egreso : egresos) {
            for (OperacionVinculable ingreso : ingresos) {
                ResultadoVinculado resultado = this.buscaOCreaResultadoNuevo(ingreso);

                if(resultado.sePuedeVincularEgreso(egreso) && cumpleCondicion(ingreso.getFecha(), egreso.getFecha()) ){
                    resultado.vincularNuevoEgreso(egreso);

                    agregarResultadoSiEsNecesario(resultado);
                    break;
                }
            }
        }
    }

    private boolean cumpleCondicion(Object parametro1, Object parametro2) {
        return  FechaUtils.estaDentroDelRango((Date) parametro1,(Date) parametro2,(int)parametro);
    }

    void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(criterioOrden);
        egresos.sort(criterioOrden);
    }

    public ArrayList<ResultadoVinculado> getResultadosVinculados(){
        return resultados;
    }

    private ResultadoVinculado buscaOCreaResultadoNuevo(OperacionVinculable ingreso) {
        for (ResultadoVinculado resultado:resultados) {
            if (resultado.contieneAlIngreso(ingreso)) {
                return resultado;
            }
        }
        return new ResultadoVinculado(ingreso);
    }

    protected CriterioOrden getCriterioOrden() {
       return null;
    }

    private void agregarResultadoSiEsNecesario(ResultadoVinculado resultadoVinculado) {
        if(resultadoVinculado.getEgresos().size() == 1){
            resultados.add(resultadoVinculado);
        }
    }

    public ETipoCriterioVinculacion getTipoCriterio() {
        return null;
    }
}
