package dominio.modelosMeli;

public class State {
    private String id;
    private String name;
    private Country country;
    private Object geoInformation;
    private City[] cities;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Country getCountry() { return country; }
    public void setCountry(Country value) { this.country = value; }

    public Object getGeoInformation() { return geoInformation; }
    public void setGeoInformation(Object value) { this.geoInformation = value; }

    public City[] getCities() { return cities; }
    public void setCities(City[] value) { this.cities = value; }
}

