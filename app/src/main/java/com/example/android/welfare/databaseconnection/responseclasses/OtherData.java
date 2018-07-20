package com.example.android.welfare.databaseconnection.responseclasses;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherData implements Serializable
{

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("emv_main_branch")
    @Expose
    private String emvMainBranch;
    @SerializedName("emv_branch")
    @Expose
    private String emvBranch;
    @SerializedName("emv_godown")
    @Expose
    private String emvGodown;
    @SerializedName("emv_factory")
    @Expose
    private String emvFactory;
    @SerializedName("emv_others")
    @Expose
    private String emvOthers;
    @SerializedName("ara_main")
    @Expose
    private String araMain;
    @SerializedName("ara_branch")
    @Expose
    private String araBranch;
    @SerializedName("ara_godown")
    @Expose
    private String araGodown;
    @SerializedName("ara_factory")
    @Expose
    private String araFactory;
    @SerializedName("ara_other")
    @Expose
    private String araOther;
    @SerializedName("organisation")
    @Expose
    private String organisation;
    private final static long serialVersionUID = 4180715748552753809L;

    /**
     * No args constructor for use in serialization
     *
     */
    public OtherData() {
    }

    /**
     *
     * @param araGodown
     * @param responseCode
     * @param araMain
     * @param emvGodown
     * @param emvBranch
     * @param organisation
     * @param emvOthers
     * @param emvFactory
     * @param araFactory
     * @param araBranch
     * @param emvMainBranch
     * @param araOther
     */
    public OtherData(Integer responseCode, String emvMainBranch, String emvBranch, String emvGodown, String emvFactory, String emvOthers, String araMain, String araBranch, String araGodown, String araFactory, String araOther, String organisation) {
        super();
        this.responseCode = responseCode;
        this.emvMainBranch = emvMainBranch;
        this.emvBranch = emvBranch;
        this.emvGodown = emvGodown;
        this.emvFactory = emvFactory;
        this.emvOthers = emvOthers;
        this.araMain = araMain;
        this.araBranch = araBranch;
        this.araGodown = araGodown;
        this.araFactory = araFactory;
        this.araOther = araOther;
        this.organisation = organisation;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getEmvMainBranch() {
        return emvMainBranch;
    }

    public void setEmvMainBranch(String emvMainBranch) {
        this.emvMainBranch = emvMainBranch;
    }

    public String getEmvBranch() {
        return emvBranch;
    }

    public void setEmvBranch(String emvBranch) {
        this.emvBranch = emvBranch;
    }

    public String getEmvGodown() {
        return emvGodown;
    }

    public void setEmvGodown(String emvGodown) {
        this.emvGodown = emvGodown;
    }

    public String getEmvFactory() {
        return emvFactory;
    }

    public void setEmvFactory(String emvFactory) {
        this.emvFactory = emvFactory;
    }

    public String getEmvOthers() {
        return emvOthers;
    }

    public void setEmvOthers(String emvOthers) {
        this.emvOthers = emvOthers;
    }

    public String getAraMain() {
        return araMain;
    }

    public void setAraMain(String araMain) {
        this.araMain = araMain;
    }

    public String getAraBranch() {
        return araBranch;
    }

    public void setAraBranch(String araBranch) {
        this.araBranch = araBranch;
    }

    public String getAraGodown() {
        return araGodown;
    }

    public void setAraGodown(String araGodown) {
        this.araGodown = araGodown;
    }

    public String getAraFactory() {
        return araFactory;
    }

    public void setAraFactory(String araFactory) {
        this.araFactory = araFactory;
    }

    public String getAraOther() {
        return araOther;
    }

    public void setAraOther(String araOther) {
        this.araOther = araOther;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

}