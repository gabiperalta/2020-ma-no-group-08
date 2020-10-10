package dominio.operaciones;

public class DocumentoComercial {
    ETipoDoc tipo;
    int numero;

    public DocumentoComercial(ETipoDoc tipo,int numero){
        this.tipo = tipo;
        this.numero = numero;
    }

    public ETipoDoc getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }
}
