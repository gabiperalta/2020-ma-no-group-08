package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.orden.CriterioOrdenValor;
import componenteVinculador.vinculable.OperacionVinculable;

import java.util.ArrayList;

public class OrdenValorPrimerEgreso extends CriterioVinculacion {

    @Override
    public void ejecutar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        CriterioOrdenValor criterio = new CriterioOrdenValor();
        this.ordenar(ingresos,egresos, criterio);

        for (OperacionVinculable egreso : egresos) {
            for (OperacionVinculable ingreso : ingresos) {
                ResultadoVinculado resultado = this.buscaOCreaResultadoNuevo(ingreso);

                if(resultado.sePuedeVincularEgreso(egreso)){
                    resultado.vincularNuevoEgreso(egreso);

                    agregarResultadoSiEsNecesario(resultado);
                    break;
                }
            }
        }
    }

    private void agregarResultadoSiEsNecesario(ResultadoVinculado resultadoVinculado) {
        if(resultadoVinculado.getEgresos().size() == 1){
            resultados.add(resultadoVinculado);
        }
    }

    private ResultadoVinculado buscaOCreaResultadoNuevo(OperacionVinculable ingreso) {
        for (ResultadoVinculado resultado:resultados) {
            if (resultado.contieneAlIngreso(ingreso)) {
                return resultado;
            }
        }
        return new ResultadoVinculado(ingreso);
    }

    @Override
    public ArrayList<ResultadoVinculado> getResultadosVinculados() {
        return resultados;
    }

 }
