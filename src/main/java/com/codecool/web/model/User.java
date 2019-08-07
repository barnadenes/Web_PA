package com.codecool.web.model;

import java.util.Objects;

public final class User extends AbstractModel {

    private String email;
    private String password;
    private String name;
    private String country;
    private String city;
    private String street;
    private String zipcode;
    private int money;
    private boolean status;

    public User(int id, String email, String password, String name, String country, String city, String street, String zipcode, int money, boolean status) {
        super(id);
        this.email = email;
        this.password = password;
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.money = money;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public int getMoney() {
        return money;
    }

    public boolean isStatus() {
        return status;
    }

    public int setMoney(int money) {
        this.money = money;
        return money;
    }

    public boolean setStatus(boolean status) {
        this.status = status;
        return status;
    }
}
