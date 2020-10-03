package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;

import java.util.ArrayList;
import java.util.Date;

public class CriterioVinculacion {
    private Object parametro;

    public CriterioVinculacion(Object parametroCondicion) {

        parametro = parametroCondicion;
    }

    public ArrayList<Vinculacion> ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos) {
        ordenar(ingresos,egresos, getCriterioOrden());
        return vincular(ingresos,egresos);
    }

    protected ArrayList<Vinculacion> vincular(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        ArrayList<Vinculacion> vinculaciones = new ArrayList<>();

        for (OperacionVinculable egreso : egresos) {
            for (OperacionVinculable ingreso : ingresos) {
                Vinculacion resultado = this.buscaOCreaResultadoNuevo(ingreso,vinculaciones);

                if(resultado.sePuedeVincularEgreso(egreso) && cumpleCondicion(ingreso.getFecha(), egreso.getFecha()) ){
                    resultado.vincularNuevoEgreso(egreso);

                    agregarResultadoSiEsNecesario(resultado, vinculaciones);
                    break;
                }
            }
        }

        return vinculaciones;
    }

    protected boolean cumpleCondicion(Object parametro1, Object parametro2) {
        return  FechaUtils.estaDentroDelRango((Date) parametro1,(Date) parametro2,(int)parametro);
    }

    void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(criterioOrden);
        egresos.sort(criterioOrden);
    }



    protected Vinculacion buscaOCreaResultadoNuevo(OperacionVinculable ingreso, ArrayList<Vinculacion> vinculaciones) {
        for (Vinculacion vinculacion : vinculaciones) {
            if (vinculacion.contieneAlIngreso(ingreso)) {
                return vinculacion;
            }
        }
        return new Vinculacion(ingreso);
    }

    protected CriterioOrden getCriterioOrden() {
       return null;
    }

    protected void agregarResultadoSiEsNecesario(Vinculacion vinculacion, ArrayList<Vinculacion> vinculaciones) {
        if(vinculacion.getEgresos().size() == 1){
            vinculaciones.add(vinculacion);
        }
    }

    public ETipoCriterioVinculacion getTipoCriterio() {
        return null;
    }
}
