package dominio.entidades;


import java.util.ArrayList;

public class RepoEntidadesBase {
    private static ArrayList<EntidadBase> entidadesBase;


    private static class RepositorioEntidadesJuridicasHolder {
        static final RepoEntidadesBase singleInstanceRepositorioEntidadesBases = new RepoEntidadesBase();
    }

    public static RepoEntidadesBase getInstance() {
        return RepoEntidadesBase.RepositorioEntidadesJuridicasHolder.singleInstanceRepositorioEntidadesBases;
    }

    public RepoEntidadesBase() {

        EntidadBase entidadBase1 = new EntidadBase("descripcion","nombre1", new OSC("3231",  "nombre1",  "33",  "4343",  "silveyra 231"));
        EntidadBase entidadBase2 = new EntidadBase("descripcion","nombre1", new OSC("123131",  "nombre",  "3121",  "23121",  "acassuso"));

        entidadesBase = new ArrayList<EntidadBase>();

        entidadesBase.add(entidadBase1);
        entidadesBase.add(entidadBase2);
    }

    public void agregarEntidadBase(String descripcion, String nombreFicticio, EntidadJuridica entidadJuridica) {
        if (this.buscarEntidadBase(nombreFicticio) == null) {
            EntidadBase entidadBase = new EntidadBase( descripcion, nombreFicticio, entidadJuridica);
            entidadesBase.add(entidadBase);
        }
    }

    }


    public void eliminarEntidadBase(String nombreFicticio) {
        EntidadBase entidadABorrar = this.buscarEntidadBase(nombreFicticio);
        if (entidadABorrar != null) {
            entidadesBase.remove(entidadABorrar);
        }
    }

    public static EntidadBase buscarEntidadBase(String nombreFicticio) {
        return entidadesBase.stream().filter(entidad -> entidad.getNombreFicticio().equals(nombreFicticio)).findFirst().get();
    }
}
