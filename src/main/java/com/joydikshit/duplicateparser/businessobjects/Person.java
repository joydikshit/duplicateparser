package com.joydikshit.duplicateparser.businessobjects;

import org.springframework.stereotype.Component;

/**
 * This class really represents each individual row obtained from either CSV files
 * We will use this for an Object representation for those records
 */
@Component
public class Person {
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String stateLong;
    private String stateCode;
    private String phoneNumber;

    //Could use Lombok - right now relying simply on IntelliJ's Generate Getters/Setters
    //Plus it's great when it works, but has been rumored to create problems when it does not

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateLong() {
        return stateLong;
    }

    public void setStateLong(String stateLong) {
        this.stateLong = stateLong;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
