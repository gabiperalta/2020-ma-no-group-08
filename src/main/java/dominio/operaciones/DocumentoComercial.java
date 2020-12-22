package dominio.operaciones;

import javax.persistence.*;

@Entity
public class DocumentoComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    ETipoDoc tipo;

    int numero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(ETipoDoc tipo) {
        this.tipo = tipo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public DocumentoComercial(ETipoDoc tipo, int numero){
        this.tipo = tipo;
        this.numero = numero;
    }

    public DocumentoComercial() {

    }

    public ETipoDoc getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }
}
