package dominio.modelosMeli;

import dominio.entidades.ETipoEmpresa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Country {
    private String id;
    private String name;
    private String locale;
    private String currencyID;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getLocale() { return locale; }
    public void setLocale(String value) { this.locale = value; }

    public String getCurrencyID() { return currencyID; }
    public void setCurrencyID(String value) { this.currencyID = value; }
}
