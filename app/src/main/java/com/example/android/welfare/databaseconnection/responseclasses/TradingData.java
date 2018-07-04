package com.example.android.welfare.databaseconnection.responseclasses;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradingData implements Serializable
{

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("firm_name")
    @Expose
    private String firmName;
    @SerializedName("firm_address")
    @Expose
    private String firmAddress;
    @SerializedName("mtp_branch")
    @Expose
    private String mtpBranch;
    @SerializedName("mtp_godown")
    @Expose
    private String mtpGodown;
    @SerializedName("mtp_factory")
    @Expose
    private String mtpFactory;
    @SerializedName("mtp_others")
    @Expose
    private String mtpOthers;
    @SerializedName("ownership_type")
    @Expose
    private String ownershipType;
    @SerializedName("capital_contribution")
    @Expose
    private String capitalContribution;
    @SerializedName("gstn_date")
    @Expose
    private String gstnDate;
    @SerializedName("license_num")
    @Expose
    private String licenseNum;
    @SerializedName("license_auth")
    @Expose
    private String licenseAuth;
    @SerializedName("official_name")
    @Expose
    private String officialName;
//    private final static long serialVersionUID = 2448122971875805156L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TradingData() {
    }

    /**
     *
     * @param ownershipType
     * @param gstnDate
     * @param licenseNum
     * @param responseCode
     * @param mtpGodown
     * @param capitalContribution
     * @param firmName
     * @param mtpBranch
     * @param licenseAuth
     * @param firmAddress
     * @param mtpFactory
     * @param officialName
     * @param mtpOthers
     */
    public TradingData(Integer responseCode, String firmName, String firmAddress, String mtpBranch,
                       String mtpGodown, String mtpFactory, String mtpOthers, String ownershipType,
                       String capitalContribution, String gstnDate, String licenseNum,
                       String licenseAuth, String officialName) {
        super();
        this.responseCode = responseCode;
        this.firmName = firmName;
        this.firmAddress = firmAddress;
        this.mtpBranch = mtpBranch;
        this.mtpGodown = mtpGodown;
        this.mtpFactory = mtpFactory;
        this.mtpOthers = mtpOthers;
        this.ownershipType = ownershipType;
        this.capitalContribution = capitalContribution;
        this.gstnDate = gstnDate;
        this.licenseNum = licenseNum;
        this.licenseAuth = licenseAuth;
        this.officialName = officialName;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmAddress() {
        return firmAddress;
    }

    public void setFirmAddress(String firmAddress) {
        this.firmAddress = firmAddress;
    }

    public String getMtpBranch() {
        return mtpBranch;
    }

    public void setMtpBranch(String mtpBranch) {
        this.mtpBranch = mtpBranch;
    }

    public String getMtpGodown() {
        return mtpGodown;
    }

    public void setMtpGodown(String mtpGodown) {
        this.mtpGodown = mtpGodown;
    }

    public String getMtpFactory() {
        return mtpFactory;
    }

    public void setMtpFactory(String mtpFactory) {
        this.mtpFactory = mtpFactory;
    }

    public String getMtpOthers() {
        return mtpOthers;
    }

    public void setMtpOthers(String mtpOthers) {
        this.mtpOthers = mtpOthers;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getCapitalContribution() {
        return capitalContribution;
    }

    public void setCapitalContribution(String capitalContribution) {
        this.capitalContribution = capitalContribution;
    }

    public String getGstnDate() {
        return gstnDate;
    }

    public void setGstnDate(String gstnDate) {
        this.gstnDate = gstnDate;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getLicenseAuth() {
        return licenseAuth;
    }

    public void setLicenseAuth(String licenseAuth) {
        this.licenseAuth = licenseAuth;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(responseCode).append(firmName).append(firmAddress).
                append(mtpBranch).append(mtpGodown).append(mtpFactory).append(mtpOthers).
                append(ownershipType).append(capitalContribution).append(gstnDate).
                append(licenseNum).append(licenseAuth).append(officialName).toString();
    }

}
