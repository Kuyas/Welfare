package com.example.android.welfare.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BankingData implements Serializable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("bank_branch")
    @Expose
    private String bankBranch;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;

    public BankingData () {

    /**
     *
     * @param responseCode
     * @param bankName
     * @param accountNumber
     * @param accountHolderName
     * @param bankBranch
     * @param ifscCode
     */
    }

    public BankingData(Integer responseCode, String bankName, String accountNumber,
                       String accountHolderName, String bankBranch, String ifscCode) {
        this.responseCode = responseCode;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.bankBranch = bankBranch;
        this.ifscCode = ifscCode;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(responseCode).append(bankName).append(accountNumber).
                append(accountHolderName).append(bankBranch).append(ifscCode);

        return stringBuilder.toString();
    }
}
