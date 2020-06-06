package dominio.entidades;

import java.util.ArrayList;

public class Organizacion {
    protected String nombre;
    protected ArrayList<EntidadJuridica> entidades;

    public Organizacion(String nombre){
        this.nombre = nombre;
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

    public void setEntidades(ArrayList<EntidadJuridica> entidades) {
        this.entidades = entidades;
    }
}
