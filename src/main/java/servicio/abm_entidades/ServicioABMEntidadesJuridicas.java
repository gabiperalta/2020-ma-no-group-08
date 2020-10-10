package servicio.abm_entidades;

import datos.RepoEntidadesJuridicas;
import datos.RepoOrganizaciones;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.*;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import datos.RepositorioUsuarios;


public class ServicioABMEntidadesJuridicas {

    public void crearEmpresa(Integer cantidadPersonal, ETipoEmpresa tipo, ETipoActividad actividad, Double promedioVentas, String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal, boolean esComisionista) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            Empresa empresa = new Empresa(tipo, cantidadPersonal,  actividad,  promedioVentas,  razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal,esComisionista);
            RepoEntidadesJuridicas.getInstance().agregarEntidadEmpresa( empresa );
        }

    }

    public void crearOSC( String razonSocial, String nombreFicticio, String cuit, String codigoIGJ, String direccionPostal) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            OSC osc = new OSC(razonSocial,  nombreFicticio,  cuit,  codigoIGJ,  direccionPostal);
            RepoEntidadesJuridicas.getInstance().agregarEntidadOSC(osc);
        }

    }


    public void eliminarEntidadJuridica(String razonSocial) throws Exception {

        if (this.buscarEntidadJuridica(razonSocial) == null) {
            RepoEntidadesJuridicas.getInstance().eliminarEntidadJuridica(razonSocial);
        }
    }


    public EntidadJuridica buscarEntidadJuridica (String razonSocial) {
        if (RepoEntidadesJuridicas.getInstance().tieneEntidad(razonSocial)){
            return RepoEntidadesJuridicas.getInstance().buscarEntidadJuridica(razonSocial);
        } else {
            return null;
        }
    }

    public void listarEntidadJuridica(String organizacion) throws Exception {
        Organizacion org = RepoOrganizaciones.buscarOrganizacion(organizacion);
        org.getEntidades();
    }

    public void recategorizar (String nombreUsuario,String razonSocial, double cantidadPersonal, double ventasPromedio) throws Exception {
        CuentaUsuario cuentaUsuario = RepositorioUsuarios.getInstance().buscarUsuario(nombreUsuario);

        EntidadJuridica entidadJuridica = buscarEntidadJuridica(razonSocial);

        if (entidadJuridica != null && cuentaUsuario.puedeRecategorizar()) {
            entidadJuridica.recategorizar(cantidadPersonal, ventasPromedio);
        } else {
            throw new Exception("No existe la entidad juridica");
        }
    }

}

