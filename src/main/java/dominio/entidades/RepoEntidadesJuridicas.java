package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;

import java.util.ArrayList;

public class RepoEntidadesJuridicas {
    private ArrayList<EntidadJuridica> entidadesJuridicas;


    private static class RepositorioEntidadesJuridicasHolder {
        static final RepoEntidadesJuridicas singleInstanceRepositorioEntidadesJuridicas = new RepoEntidadesJuridicas();
    }

    public static RepoEntidadesJuridicas getInstance() {
        return RepoEntidadesJuridicas.RepositorioEntidadesJuridicasHolder.singleInstanceRepositorioEntidadesJuridicas;
    }

    public RepoEntidadesJuridicas() {

        EntidadJuridica entidad1 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "0001", "ficticia1", "23453456", "200", "caba 322",false);
        EntidadJuridica entidad2 = new OSC("DDS SA","pesi","27975312460","200","Cordoba 111");
        EntidadJuridica entidad3 = new Empresa(ETipoEmpresa.MICRO, 1, ETipoActividad.COMERCIO, 100.00, "0001", "ficticia2", "4453456", "200", "silveyra 4343",true);

        entidadesJuridicas = new ArrayList<EntidadJuridica>();

        entidadesJuridicas.add(entidad1);
        entidadesJuridicas.add(entidad2);
        entidadesJuridicas.add(entidad3);
    }

    public void agregarEntidadEmpresa(ETipoEmpresa tipo, Integer cantidadPersonal, ETipoActividad actividad, Double promedioVentas, String razonSocial,
                                      String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal, boolean esComisionista) {
        if (this.buscarEntidadJuridica(razonSocial) == null) {
            EntidadJuridica entidadJuridica = new Empresa( tipo, cantidadPersonal,  actividad,  promedioVentas,  razonSocial,
                     nombreFicticio,  cuit,  codigoIGJ,  direccionPostal, esComisionista);
            entidadesJuridicas.add(entidadJuridica);
        }
    }

    public void agregarEntidadOSC(String razonSocial,
                                      String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) {
        if (this.buscarEntidadJuridica(razonSocial) == null) {
            EntidadJuridica entidadJuridica = new OSC(razonSocial, nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
            entidadesJuridicas.add(entidadJuridica);
        }
    }


    public void eliminarEntidadJuridica(String razonSocial) {
        EntidadJuridica entidadABorrar = this.buscarEntidadJuridica(razonSocial);
        if (entidadABorrar != null) {
            entidadesJuridicas.remove(entidadABorrar);
        }
    }

    public EntidadJuridica buscarEntidadJuridica(String razonSocial) {
        return entidadesJuridicas.stream().filter(entidad -> entidad.getRazonSocial().equals(razonSocial)).findFirst().get();
    }
}
