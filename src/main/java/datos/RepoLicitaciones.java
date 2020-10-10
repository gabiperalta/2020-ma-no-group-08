package datos;

import dominio.licitacion.Licitacion;

import java.util.ArrayList;

public class RepoLicitaciones {
    private static ArrayList<Licitacion> licitaciones;
    private static int ultimoIdentificador;

    private static class RepositorioLicitacionesHolder {
        static final RepoLicitaciones singleInstanceRepositorioLicitaciones = new RepoLicitaciones();
    }

    public static RepoLicitaciones getInstance() {
        return RepoLicitaciones.RepositorioLicitacionesHolder.singleInstanceRepositorioLicitaciones;
    }

    public RepoLicitaciones() {
        licitaciones = new ArrayList<>();
        ultimoIdentificador = 1 ;
    }

    public void agregarLicitacion(Licitacion licitacion){
        String identificador = "L-";
        licitacion.setIdentificador(identificador + ultimoIdentificador);
        ultimoIdentificador++;
        licitaciones.add(licitacion);
    }

    public Licitacion buscarLicitacionPorOperacionEgreso(String identificadorOperacionEgreso){
        if(licitaciones.stream().anyMatch(licitacion -> licitacion.getOperacionEgreso().getIdentificador().equals(identificadorOperacionEgreso)))
            return licitaciones.stream().filter(licitacion -> licitacion.getOperacionEgreso().getIdentificador().equals(identificadorOperacionEgreso)).findFirst().get();
        else
            return null;
    }

    public Licitacion buscarLicitacionPorIdentificador(String identificadorLicitacion){
        if(licitaciones.stream().anyMatch(licitacion -> licitacion.getIdentificador().equals(identificadorLicitacion)))
            return licitaciones.stream().filter(licitacion -> licitacion.getIdentificador().equals(identificadorLicitacion)).findFirst().get();
        else
            return null;
    }

    public ArrayList<Licitacion> getLicitaciones(){
        return licitaciones;
    }
}

