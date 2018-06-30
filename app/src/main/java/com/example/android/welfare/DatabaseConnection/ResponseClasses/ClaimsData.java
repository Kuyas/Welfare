package com.example.android.welfare.DatabaseConnection.ResponseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimsData {

    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("application_type")
    @Expose
    private String applicationType;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public ClaimsData() {
    }

    /**
     *
     * @param responseCode
     * @param status
     * @param applicationType
     */
    public ClaimsData(int responseCode, String applicationType, String status) {
        super();
        this.responseCode = responseCode;
        this.applicationType = applicationType;
        this.status = status;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}