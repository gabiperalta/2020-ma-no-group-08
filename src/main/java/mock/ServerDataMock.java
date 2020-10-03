package mock;

import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;

public class ServerDataMock {

    public void cargarMock() throws Exception {
        cargarIngresos();
        cargarEgregos();
    }



    private void cargarIngresos() throws Exception {
        OperacionIngreso ingreso1 = new OperacionIngreso();
        OperacionIngreso ingreso2 = new OperacionIngreso();
        OperacionIngreso ingreso3 = new OperacionIngreso();

        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso1);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso2);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso3);
    }

    private void cargarEgregos() {
//        OperacionEgreso egreso = ;
//        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso);
    }
}
