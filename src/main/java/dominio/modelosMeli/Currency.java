package dominio.modelosMeli;

public class Currency {
    private String id;
    private String description;
    private String symbol;
    private long decimalPlaces;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String value) { this.symbol = value; }

    public long getDecimalPlaces() { return decimalPlaces; }
    public void setDecimalPlaces(long value) { this.decimalPlaces = value; }
}
