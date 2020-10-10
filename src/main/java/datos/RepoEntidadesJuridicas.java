package datos;

import dominio.entidades.ETipoEmpresa;
import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.OSC;
import dominio.entidades.calculadorFiscal.ETipoActividad;


import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RepoEntidadesJuridicas {
    private static ArrayList<EntidadJuridica> entidadesJuridicas;

    public boolean tieneEntidad(String razonSocial) {
        try {
            buscarEntidadJuridica(razonSocial);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


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

        EntidadJuridica entidad4 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "Empresa 1", "Empresa 1", "20-40678950-3", "200", "Av.Libertador 801",false);


        entidadesJuridicas = new ArrayList<EntidadJuridica>();

        entidadesJuridicas.add(entidad1);
        entidadesJuridicas.add(entidad2);
        entidadesJuridicas.add(entidad3);
        entidadesJuridicas.add(entidad4);

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
        EntidadJuridica entidadABorrar = buscarEntidadJuridica(razonSocial);
        if (entidadABorrar != null) {
            entidadesJuridicas.remove(entidadABorrar);
        }
    }

    public static EntidadJuridica buscarEntidadJuridica(String razonSocial) {
        return entidadesJuridicas.stream().filter(entidad -> entidad.getRazonSocial().equals(razonSocial)).findFirst().get();
    }

}
