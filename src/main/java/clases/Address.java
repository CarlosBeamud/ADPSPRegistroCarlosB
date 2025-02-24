package clases;

public class Address {
    private String street;
    private Integer number;
    private String postalCode;

    public Address(String street, Integer number, String postalCode) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    public Address() {

    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
