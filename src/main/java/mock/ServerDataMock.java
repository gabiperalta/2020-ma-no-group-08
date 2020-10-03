package mock;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;

import java.util.Date;

public class ServerDataMock {

    public void cargarMock() throws Exception {
        cargarIngresos();
        cargarEgregos();
    }



    private void cargarIngresos() throws Exception {
        OperacionIngreso ingreso1 = new OperacionIngreso();
        ingreso1.setFecha(new Date());
        ingreso1.setMontoTotal(1000);

        OperacionIngreso ingreso2 = new OperacionIngreso();
        ingreso1.setFecha(new Date());
        ingreso1.setMontoTotal(2000);
        
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso1);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso2);
    }

    private void cargarEgregos() {
//        OperacionEgreso egreso = ;
//        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso);
    }
}
