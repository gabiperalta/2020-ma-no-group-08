package dominio.licitacion;

import net.sf.ezmorph.object.IdentityObjectMorpher;

import java.util.ArrayList;

public class RepoLicitaciones {
    private static ArrayList<Licitacion> licitaciones;
    private static int ultimoIdentificador;

    private static class RepositorioLicitacionesHolder {
        static final dominio.licitacion.RepoLicitaciones singleInstanceRepositorioLicitaciones = new dominio.licitacion.RepoLicitaciones();
    }

    public static dominio.licitacion.RepoLicitaciones getInstance() {
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
        return licitaciones.stream().filter(licitacion -> licitacion.getOperacionEgreso().getIdentificador().equals(identificadorOperacionEgreso)).findFirst().get();
    }

    public Licitacion buscarLicitacionPorIdentificador(String identificadorLicitacion){
        return licitaciones.stream().filter(licitacion -> licitacion.getIdentificador().equals(identificadorLicitacion)).findFirst().get();
    }
}

