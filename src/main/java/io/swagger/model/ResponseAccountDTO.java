package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

//This DTO is created The Response bodies of Account endpoints.
// There are certain user and account information that can not be shown.
// Therefore I have created this Class
public class ResponseAccountDTO {

    @JsonProperty("Name of Account owner:")
    private String nameOfUser;

    @JsonProperty("IBAN")
    private String iban;

    @JsonProperty("Account status:")
    private Account.StatusEnum accountStatus;

    @JsonProperty("Account Balance:")
    private BigDecimal balance;

    @JsonProperty("Absolute Limit:")
    private BigDecimal absoluteLimit;

    @JsonProperty("Type of Account:")
    private Account.TypeEnum accountType;

    //Constructors
    public ResponseAccountDTO(){

    }
    public ResponseAccountDTO(String nameOfUser, String IBAN, Account.StatusEnum status, Account.TypeEnum type,BigDecimal balance, BigDecimal absoluteLimit) {
        this.nameOfUser = nameOfUser;
        this.iban = IBAN;
        this.accountStatus = status;
        this.accountType=type;
        this.balance = balance;
        this.absoluteLimit = absoluteLimit;
    }


    //Getters and Setters
    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(BigDecimal absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }

    public Account.StatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Account.StatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Account.TypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(Account.TypeEnum accountType) {
        this.accountType = accountType;
    }
}
