package dominio.operaciones.medioDePago;

import javax.persistence.*;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDePago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    public MedioDePago(){  }

    public abstract void informacionARegistrar();
    public abstract String getNombre();
    public abstract double getMonto();
    public abstract String getPuntoDePago();
    public abstract int getCuotas();
    public abstract String getNumero();
    public abstract boolean getEsDineroEnCuenta();
    public abstract boolean getEsEfectivo();
    public abstract boolean getEsTarjetaDeCredito();
    public abstract boolean getEsTarjetaDeDebito();
}
