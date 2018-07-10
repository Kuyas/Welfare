package com.example.android.welfare.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TradingData implements Serializable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("firm_name")
    @Expose
    private String firmName;
    @SerializedName("firm_address")
    @Expose
    private String firmAddress;
    @SerializedName("annual_turnover")
    @Expose
    private String annualTurnover;
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
    @SerializedName("ownership")
    @Expose
    private String ownership;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("license_num")
    @Expose
    private String licenseNum;
    @SerializedName("license_auth")
    @Expose
    private String licenseAuth;
    @SerializedName("official")
    @Expose
    private String official;

    /**
     * No args constructor for use in serialization
     */
    public TradingData() {
    }

    /**
     * @param licenseNum
     * @param responseCode
     * @param mtpGodown
     * @param firmName
     * @param mtpBranch
     * @param annualTurnover
     * @param capital
     * @param licenseAuth
     * @param firmAddress
     * @param ownership
     * @param official
     * @param mtpFactory
     * @param gstn
     * @param mtpOthers
     */
    public TradingData(Integer responseCode, String firmName, String firmAddress, String annualTurnover, String mtpBranch, String mtpGodown, String mtpFactory, String mtpOthers, String ownership, String capital, String gstn, String licenseNum, String licenseAuth, String official) {
        super();
        this.responseCode = responseCode;
        this.firmName = firmName;
        this.firmAddress = firmAddress;
        this.annualTurnover = annualTurnover;
        this.mtpBranch = mtpBranch;
        this.mtpGodown = mtpGodown;
        this.mtpFactory = mtpFactory;
        this.mtpOthers = mtpOthers;
        this.ownership = ownership;
        this.capital = capital;
        this.gstn = gstn;
        this.licenseNum = licenseNum;
        this.licenseAuth = licenseAuth;
        this.official = official;
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

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
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

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
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

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

}