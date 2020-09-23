package dominio.modelosMeli;

public class Conversion {
    private double ratio;
    private double mercadoPagoRatio;
    private String idFrom;
    private String idTo;




    public double getRatio() { return ratio; }
    public void setRatio(double value) { this.ratio = value; }

    public void setIdFrom(String value) { this.idFrom = value; }

    public String getIdFrom() { return idFrom; }

    public void setIdTo(String value) { this.idTo = value; }

    public String getIdTo() { return idTo; }


    public double getMercadoPagoRatio() { return mercadoPagoRatio; }
    public void setMercadoPagoRatio(double value) { this.mercadoPagoRatio = value; }
}