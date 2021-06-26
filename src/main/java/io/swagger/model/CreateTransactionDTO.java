package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransactionDTO {

    @JsonProperty("senderIBAN")
    private String senderIBAN = null;

    @JsonProperty("receiverIBAN")
    private String receiverIBAN = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("currencyType")
    private String currencyType = null;

    public CreateTransactionDTO(String senderIBAN, String receiverIBAN, Double amount, String currencyType) {
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
