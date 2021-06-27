package io.swagger.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Transaction;
import io.swagger.service.AccountService;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.Id;

public class TransactionDTO {

    @JsonProperty("transactionID")
    @Id
    private long transactionID;

    @JsonProperty("dateTimeCreated")
    private OffsetDateTime dateTimeCreated = null;

    @JsonProperty("senderUserID")
    private long senderUserID;

    @JsonProperty("senderIBAN")
    private String senderIBAN = null;

    @JsonProperty("receiverIBAN")
    private String receiverIBAN = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("currencyType")
    private String currencyType = null;

    public TransactionDTO() {
    }

    public TransactionDTO(long transactionID, OffsetDateTime dateTimeCreated, long senderUserID, String senderIBAN, String receiverIBAN, Double amount, String currencyType) {
        this.dateTimeCreated = dateTimeCreated;
        this.transactionID = transactionID;
        this.senderUserID = senderUserID;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public TransactionDTO(long senderUserID, String senderIBAN, String receiverIBAN, Double amount, String currencyType) {
        this.senderUserID = senderUserID;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    //convert TransactionDTO to Transaction
    public Transaction getTransaction(AccountService accountService) {
        return new Transaction(accountService.getAccountByIban(senderIBAN)
                , accountService.getAccountByIban(receiverIBAN)
                , this.amount, this.currencyType);
    }

    public OffsetDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(OffsetDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount cannot be less or equal to zero.");
        this.amount = amount;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }


    public void setSenderUserID(long senderUserID) {
        this.senderUserID = senderUserID;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public long getSenderUserID() {
        return senderUserID;
    }
}
