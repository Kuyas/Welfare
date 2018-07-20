package com.example.android.welfare.databaseconnection.responseclasses;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyData implements Serializable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("relationship")
    @Expose
    private String relationship;
    private final static long serialVersionUID = -3967715201955920315L;

    /**
     * No args constructor for use in serialization
     */
    public FamilyData() {
    }

    /**
     * @param relationship
     * @param responseCode
     * @param occupation
     * @param age
     * @param name
     * @param gender
     */
    public FamilyData(Integer responseCode, String name, String age, String gender, String occupation, String relationship) {
        super();
        this.responseCode = responseCode;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.relationship = relationship;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(responseCode).append(name).append(age).append(gender).append(occupation).append(relationship).toString();
    }
}