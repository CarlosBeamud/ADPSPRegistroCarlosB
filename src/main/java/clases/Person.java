package clases;

import java.util.Arrays;
import java.util.List;

public class Person {
    private String username;
    private String name;
    private Integer age;
    private List<String> emails;
    private byte[] password;
    private Address address;

    public Person(String username, byte[] password) {
        this.username = username;
        this.password = password;
    }

    public Person(String username, String name, Integer age, List<String> emails, byte[] password, Address address) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.emails = emails;
        this.password = password;
        this.address = address;
    }

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", emails=" + emails +
                ", address=" + address.toString() +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
