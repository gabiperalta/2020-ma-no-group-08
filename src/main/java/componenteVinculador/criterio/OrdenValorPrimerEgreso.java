package componenteVinculador.criterio;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.vinculable.OperacionVinculable;
import dominio.vinculacion.criterio.OrdenarEgresosPorValor;
import java.util.ArrayList;

public class OrdenValorPrimerEgreso implements CriterioVinculacion {
    private ArrayList<ResultadoVinculado> resultados;
    
    @Override
    public void ejecutar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        this.ordenar(ingresos,egresos);
        resultados = new ArrayList<>();

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
    public void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        CriterioOrdenValor criterio = new CriterioOrdenValor();
        ingresos.sort(criterio);
        egresos.sort(criterio);
    }

    @Override
    public ArrayList<ResultadoVinculado> getResultadosVinculados() {
        return resultados;
    }

 }
