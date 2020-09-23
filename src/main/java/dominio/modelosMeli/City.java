package dominio.modelosMeli;


public class City {
    private String id;
    private String name;
    private State state;
    private Country country;
    private Object geoInformation;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public State getState() { return state; }
    public void setState(State value) { this.state = value; }

    public Country getCountry() { return country; }
    public void setCountry(Country value) { this.country = value; }

    public Object getGeoInformation() { return geoInformation; }
    public void setGeoInformation(Object value) { this.geoInformation = value; }
}