package dominio.entidades;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.EntidadOperacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Organizaciones")
public class Organizacion {
    @Id @GeneratedValue
    private int id;

    private String nombre;
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "nombre_organizacion")
    private List<Empresa> entidades;

    public Organizacion() {}
    public Organizacion(String nombre, ArrayList<Empresa> entidades){
        this.nombre = nombre;
        this.entidades= entidades;

    }

    public void agregarEntidad(Empresa entidad) {
        this.entidades.add(entidad);
    }

    public void quitarEntidad(Empresa entidad) {
        this.entidades.remove(entidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Empresa> getEntidades() {
        return new ArrayList<>(entidades);
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

    public Empresa buscarEntidad(String razonSocial){
        Empresa unaEntidadJuridica = entidades.stream().filter( entidad -> entidad.getRazonSocial().equals(razonSocial)).findFirst().get();
        return unaEntidadJuridica;
    }
    public Empresa buscarEntidadPorCuit(String cuit){
        Empresa unaEntidadJuridica = entidades.stream().filter( entidad -> entidad.getCuit().equals(cuit)).findFirst().get();
        return unaEntidadJuridica;
    }


    public void setEntidades(ArrayList<Empresa> entidades) {
        this.entidades = entidades;
    }
}
