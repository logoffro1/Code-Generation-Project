package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.threeten.bp.OffsetDateTime;

public class TransactionDTO
{

    @JsonProperty("dateTimeCreated")
    private OffsetDateTime dateTimeCreated = null;

    @JsonProperty("senderIBAN")
    private String senderIBAN = null;

    @JsonProperty("receiverIBAN")
    private String receiverIBAN = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("currencyType")
    private String currencyType = null;

    public TransactionDTO()
    {
    }

    public TransactionDTO(String senderIBAN, String receiverIBAN, Double amount, String currencyType)
    {
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public OffsetDateTime getDateTimeCreated()
    {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(OffsetDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public String getCurrencyType()
    {
        return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
        this.currencyType = currencyType;
    }

    public String getSenderIBAN()
    {
        return senderIBAN;
    }

    public String getReceiverIBAN()
    {
        return receiverIBAN;
    }


}
