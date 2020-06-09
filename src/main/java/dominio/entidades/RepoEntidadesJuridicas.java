package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.perfil.PerfilAdministrador;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;
import temporal.seguridad.repositorioUsuarios.exceptions.CredencialesNoValidasException;


import java.util.ArrayList;

public class RepoEntidadesJuridicas {
    private static ArrayList<EntidadJuridica> entidadesJuridicas;


    private static class RepositorioEntidadesJuridicasHolder {
        static final RepoEntidadesJuridicas singleInstanceRepositorioEntidadesJuridicas = new RepoEntidadesJuridicas();
    }

    public static RepoEntidadesJuridicas getInstance() {
        return RepoEntidadesJuridicas.RepositorioEntidadesJuridicasHolder.singleInstanceRepositorioEntidadesJuridicas;
    }

    public RepoEntidadesJuridicas() {

        EntidadJuridica entidad1 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "0001", "ficticia1", "23453456", "200", "caba 322",false);
        EntidadJuridica entidad2 = new OSC("0002", "ficticia3", "23131", "111", "obligado 11");
        EntidadJuridica entidad3 = new Empresa(ETipoEmpresa.MICRO, 1, ETipoActividad.COMERCIO, 100.00, "0001", "ficticia2", "4453456", "200", "silveyra 4343",true);

        entidadesJuridicas = new ArrayList<EntidadJuridica>();

        entidadesJuridicas.add(entidad1);
        entidadesJuridicas.add(entidad2);
        entidadesJuridicas.add(entidad3);
    }


    public void agregarEntidadEmpresa(Empresa empresa) {
        if (this.buscarEntidadJuridica(empresa.getRazonSocial()) == null) {
            entidadesJuridicas.add(empresa);
        }
    }

    public void agregarEntidadOSC(OSC osc) {
        if (this.buscarEntidadJuridica(osc.getRazonSocial()) == null) {
            entidadesJuridicas.add(osc);
        }
    }


    public void eliminarEntidadJuridica(String razonSocial) {
        EntidadJuridica entidadABorrar = this.buscarEntidadJuridica(razonSocial);
        if (entidadABorrar != null) {
            entidadesJuridicas.remove(entidadABorrar);
        }
    }

    public static EntidadJuridica buscarEntidadJuridica(String razonSocial) {
        return entidadesJuridicas.stream().filter(entidad -> entidad.getRazonSocial().equals(razonSocial)).findFirst().get();
    }
}
