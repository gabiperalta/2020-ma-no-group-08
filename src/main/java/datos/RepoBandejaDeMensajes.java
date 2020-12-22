package datos;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.notificador_suscriptores.bandeja_de_mensajes.BandejaDeMensajes;

import java.util.ArrayList;

public class RepoBandejaDeMensajes {

    private static ArrayList<BandejaDeMensajes> bandejasDeMensajes;

    private static class RepositorioBandejaDeMensajes {
        static final RepoBandejaDeMensajes singleInstanceRepositorioBandejaDeMensajes = new RepoBandejaDeMensajes();
    }

    public static RepoBandejaDeMensajes getInstance() {
        return RepositorioBandejaDeMensajes.singleInstanceRepositorioBandejaDeMensajes;
    }

    public RepoBandejaDeMensajes() {
        bandejasDeMensajes = new ArrayList<>();
    }

    public void agregarBandejaDeMensajes(BandejaDeMensajes bandejaDeMensajes) {
        bandejasDeMensajes.add(bandejaDeMensajes);
    }

    public BandejaDeMensajes buscarBandejaDeMensajes(CuentaUsuario cuentaUsuario){
        return bandejasDeMensajes.stream().filter(bandeja -> bandeja.getUsuario().equals(cuentaUsuario)).findFirst().get(); // si no encuentra nada, tira NoSuchElementException
    }

    public ArrayList<BandejaDeMensajes> getBandejasDeMensajes(){
        return bandejasDeMensajes;
    }

}
