package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.EntidadOperacion;

import java.util.ArrayList;

public class Organizacion {
    protected String nombre;
    protected ArrayList<EntidadJuridica> entidades;

    public Organizacion(String nombre, ArrayList<EntidadJuridica> entidades){
        this.nombre = nombre;
        this.entidades= entidades;

    }

    public void agregarEntidad(EntidadJuridica entidad) {
        this.entidades.add(entidad);
    }

    public void quitarEntidad(EntidadJuridica entidad) {
        this.entidades.remove(entidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<EntidadJuridica> getEntidades() {
        return entidades;
    }

    public boolean existeLaEntidad(String razonSocial) {
        boolean existeLaEntidad;
        try {
            this.buscarEntidad(razonSocial);
            existeLaEntidad = true;
        }
        catch (Exception NoSuchElementException){
            existeLaEntidad = false;
        }
        return existeLaEntidad;
    }

    public EntidadJuridica buscarEntidad(String razonSocial){
        EntidadJuridica unaEntidadJuridica = entidades.stream().filter( entidad -> entidad.getRazonSocial().equals(razonSocial)).findFirst().get();
        return unaEntidadJuridica;
    }
    public EntidadJuridica buscarEntidadPorCuit(String cuit){
        EntidadJuridica unaEntidadJuridica = entidades.stream().filter( entidad -> entidad.getCuit().equals(cuit)).findFirst().get();
        return unaEntidadJuridica;
    }


    public void setEntidades(ArrayList<EntidadJuridica> entidades) {
        this.entidades = entidades;
    }
}
