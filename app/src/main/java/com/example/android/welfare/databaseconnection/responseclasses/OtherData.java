package com.example.android.welfare.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherData implements Serializable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("own_main_branch")
    @Expose
    private Integer ownMainBranch;
    @SerializedName("own_branch")
    @Expose
    private Integer ownBranch;
    @SerializedName("own_godown")
    @Expose
    private Integer ownGodown;
    @SerializedName("own_factory")
    @Expose
    private Integer ownFactory;
    @SerializedName("own_other")
    @Expose
    private Integer ownOther;
    @SerializedName("rented_main_branch")
    @Expose
    private Integer rentedMainBranch;
    @SerializedName("rented_branch")
    @Expose
    private Integer rentedBranch;
    @SerializedName("rented_godown")
    @Expose
    private Integer rentedGodown;
    @SerializedName("rented_factory")
    @Expose
    private Integer rentedFactory;
    @SerializedName("rented_other")
    @Expose
    private Integer rentedOther;
    @SerializedName("traders_organisation_name")
    @Expose
    private Integer tradersOrganisationName;


    public OtherData() {
        /**
         *
         * @param responseCode
         * @param ownMainBranch
         * @param ownBranch
         * @param ownGodown
         * @param ownFactory
         * @param ownOther
         * @param rentedMainBranch
         * @param rentedBranch
         * @param rentedGodown
         * @param rentedFactory
         * @param rentedOther
         * @param tradersOrganisationName
         */
    }

    public OtherData(Integer responseCode, Integer ownMainBranch, Integer ownBranch,
                     Integer ownGodown, Integer ownFactory, Integer ownOther,
                     Integer rentedMainBranch, Integer rentedBranch, Integer rentedGodown,
                     Integer rentedFactory, Integer rentedOther, Integer tradersOrganisationName) {
        this.responseCode = responseCode;
        this.ownMainBranch = ownMainBranch;
        this.ownBranch = ownBranch;
        this.ownGodown = ownGodown;
        this.ownFactory = ownFactory;
        this.ownOther = ownOther;
        this.rentedMainBranch = rentedMainBranch;
        this.rentedBranch = rentedBranch;
        this.rentedGodown = rentedGodown;
        this.rentedFactory = rentedFactory;
        this.rentedOther = rentedOther;
        this.tradersOrganisationName = tradersOrganisationName;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getOwnMainBranch() {
        return ownMainBranch;
    }

    public void setOwnMainBranch(Integer ownMainBranch) {
        this.ownMainBranch = ownMainBranch;
    }

    public Integer getOwnBranch() {
        return ownBranch;
    }

    public void setOwnBranch(Integer ownBranch) {
        this.ownBranch = ownBranch;
    }

    public Integer getOwnGodown() {
        return ownGodown;
    }

    public void setOwnGodown(Integer ownGodown) {
        this.ownGodown = ownGodown;
    }

    public Integer getOwnFactory() {
        return ownFactory;
    }

    public void setOwnFactory(Integer ownFactory) {
        this.ownFactory = ownFactory;
    }

    public Integer getOwnOther() {
        return ownOther;
    }

    public void setOwnOther(Integer ownOther) {
        this.ownOther = ownOther;
    }

    public Integer getRentedMainBranch() {
        return rentedMainBranch;
    }

    public void setRentedMainBranch(Integer rentedMainBranch) {
        this.rentedMainBranch = rentedMainBranch;
    }

    public Integer getRentedBranch() {
        return rentedBranch;
    }

    public void setRentedBranch(Integer rentedBranch) {
        this.rentedBranch = rentedBranch;
    }

    public Integer getRentedGodown() {
        return rentedGodown;
    }

    public void setRentedGodown(Integer rentedGodown) {
        this.rentedGodown = rentedGodown;
    }

    public Integer getRentedFactory() {
        return rentedFactory;
    }

    public void setRentedFactory(Integer rentedFactory) {
        this.rentedFactory = rentedFactory;
    }

    public Integer getRentedOther() {
        return rentedOther;
    }

    public void setRentedOther(Integer rentedOther) {
        this.rentedOther = rentedOther;
    }

    public Integer getTradersOrganisationName() {
        return tradersOrganisationName;
    }

    public void setTradersOrganisationName(Integer tradersOrganisationName) {
        this.tradersOrganisationName = tradersOrganisationName;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(responseCode).append(ownMainBranch).append(ownBranch).append(ownGodown).
                append(ownFactory).append(ownOther).append(rentedMainBranch).append(rentedBranch).
                append(rentedGodown).append(rentedFactory).append(rentedOther).
                append(tradersOrganisationName);

        return stringBuilder.toString();
    }
}
