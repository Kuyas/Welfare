package com.example.android.welfare.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TurnoverData {


    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("turnover")
    @Expose
    private String turnover;

    /**
     * No args constructor for use in serialization
     *
     */
    public TurnoverData() {
    }

    /**
     *
     * @param responseCode
     * @param turnover
     */
    public TurnoverData(int responseCode, String turnover) {
        super();
        this.responseCode = responseCode;
        this.turnover = turnover;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }
}
