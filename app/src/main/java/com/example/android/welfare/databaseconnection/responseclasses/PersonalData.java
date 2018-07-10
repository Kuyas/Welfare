package com.example.android.welfare.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PersonalData implements Serializable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("district")
    @Expose
    private String district;
//    private final static long serialVersionUID = -1713650480136730661L;

    /**
     * No args constructor for use in serialization
     */
    public PersonalData() {
    }

    /**
     * @param responseCode
     * @param address
     * @param dob
     * @param name
     * @param gender
     * @param district
     * @param place
     */
    public PersonalData(Integer responseCode, String name, String dob, String gender,
                        String address, String place, String district) {
        super();
        this.responseCode = responseCode;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.place = place;
        this.district = district;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(responseCode).append(name).append(dob).append(gender).
                append(address).append(place).append(district).toString();
    }

}
