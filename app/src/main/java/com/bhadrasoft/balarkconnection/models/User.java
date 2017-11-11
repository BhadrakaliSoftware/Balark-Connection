package com.bhadrasoft.balarkconnection.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer birthDate;
    private String gautra;
    private String birthPlace;
    private boolean isMale;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public String getGautra() {
        return gautra;
    }

    public void setGautra(String gautra) {
        this.gautra = gautra;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
