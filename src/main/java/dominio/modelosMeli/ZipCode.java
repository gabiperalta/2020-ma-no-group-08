package dominio.modelosMeli;

public class ZipCode {
    private String zipCode;
    private City city;
    private State state;
    private Country country;

    public String getZipCode() { return zipCode; }
    public void setZipCode(String value) { this.zipCode = value; }

    public City getCity() { return city; }
    public void setCity(City value) { this.city = value; }

    public State getState() { return state; }
    public void setState(State value) { this.state = value; }

    public Country getCountry() { return country; }
    public void setCountry(Country value) { this.country = value; }
}