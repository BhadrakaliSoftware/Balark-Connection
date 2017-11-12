package com.bhadrasoft.balarkconnection.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String GAUTRA = "gautra";
    public static final String GENDER = "gender";
    public static final String NATIVE = "nativePlace";
    public static final String BIRTH_PLACE = "birthPlace";
    public static final String BIRTH_TIME = "birthTime";
    public static final String BLOOD_GROUP = "bloodGroup";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";

    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gautra;
    private boolean isMale;
    private String birthPlace;
    private String birthTime;
    private String bloodGroup;
    private String height;
    private String weight;
    private String email;
    private Integer birthDate;
    private String nativePlace;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public User() {
    }

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
}
