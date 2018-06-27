package com.example.android.welfare.DatabaseConnection.ResponseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalPostData {
    @SerializedName("response_code")
    @Expose
    private Long responseCode;
    @SerializedName("id")
    @Expose
    private String id;

    public PersonalPostData() {
    }

    public PersonalPostData(Long responseCode, String id) {
        super();
        this.responseCode = responseCode;
        this.id = id;
    }

    public Long getResponseCode() {   return responseCode; }

    public void setResponseCode(Long responseCode) { this.responseCode = responseCode; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
