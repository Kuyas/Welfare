package com.example.android.welfare.DatabaseConnection.ResponseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPostData {

    @SerializedName("response_code")
    @Expose
    private Long responseCode;
    @SerializedName("id")
    @Expose
    private String id;

    public Long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Long responseCode) {
        this.responseCode = responseCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}