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
    private Integer bankName;
    @SerializedName("account_number")
    @Expose
    private Integer accountNumber;
    @SerializedName("account_holder_name")
    @Expose
    private Integer accountHolderName;
    @SerializedName("bank_branch")
    @Expose
    private Integer bankBranch;
    @SerializedName("ifsc_code")
    @Expose
    private Integer ifscCode;

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

    public BankingData(Integer responseCode, Integer bankName, Integer accountNumber,
                       Integer accountHolderName, Integer bankBranch, Integer ifscCode) {
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

    public Integer getBankName() {
        return bankName;
    }

    public void setBankName(Integer bankName) {
        this.bankName = bankName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(Integer accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Integer getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(Integer bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Integer getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(Integer ifscCode) {
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
