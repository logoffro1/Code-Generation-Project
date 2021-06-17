package io.swagger.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.engine.internal.Cascade;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")

@Entity
public class Transaction
{

    @JsonProperty("transactionId")
    @Id
    @GeneratedValue
    private long transactionId;

    @JsonProperty("dateTimeCreated")
    private OffsetDateTime dateTimeCreated = null;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "senderIBAN")
    private Account senderAccount = null;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverIBAN")
    private Account receiverAccount = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("amountLimit")
    private final Double AMOUNT_LIMIT = 10000.00;

    @JsonProperty("currencyType")
    private String currencyType = null;

    public Transaction()
    {
    }

    public Transaction(Account senderAccount, Account receiverAccount, Double amount, String currencyType)
    {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public Transaction transactionId(Integer transactionId)
    {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionDTO getTransactionDTO()
    {
        return new TransactionDTO(senderAccount.getIBAN(), receiverAccount.getIBAN(), this.amount, this.currencyType);
    }

    /**
     * Get transactionId
     *
     * @return transactionId
     **/
    @Schema(example = "2012", description = "")

    public long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId)
    {
        this.transactionId = transactionId;
    }

    public Transaction dateTimeCreated(OffsetDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
        return this;
    }

    /**
     * the date and time the transaction was created
     *
     * @return dateTimeCreated
     **/
    @Schema(description = "the date and time the transaction was created")

    @Valid
    public OffsetDateTime getDateTimeCreated()
    {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(OffsetDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Transaction senderAccount(Account senderAccount)
    {
        this.senderAccount = senderAccount;
        return this;
    }

    /**
     * Get senderIBAN
     *
     * @return senderIBAN
     **/
    @Schema(example = "NL23RABO2298608059", description = "")

    public Account getSenderAccount()
    {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount)
    {
        this.senderAccount = senderAccount;
    }

    public Transaction receiverAccount(Account receiverAccount)
    {
        this.receiverAccount = receiverAccount;
        return this;
    }

    /**
     * Get receiverIBAN
     *
     * @return receiverIBAN
     **/
    @Schema(example = "NL67ABNA8265634552", description = "")

    public Account getReceiverAccount()
    {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount)
    {
        this.receiverAccount = receiverAccount;
    }

    /**
     * Get senderUserId
     *
     * @return senderUserId
     **/
    @Schema(example = "20939", description = "")


    public Transaction amount(Double amount)
    {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     *
     * @return amount
     **/
    @Schema(example = "4800", description = "")

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

  /*public Transaction amountLimit(Double amountLimit) {
    this.AMOUNT_LIMIT = AMOUNT_LIMIT;
    return this;
  }*/

    /**
     * Get amountLimit
     *
     * @return amountLimit
     **/
    @Schema(example = "4800", description = "")

    public Double getAmountLimit()
    {
        return AMOUNT_LIMIT;
    }

    public Transaction currencyType(String currencyType)
    {
        this.currencyType = currencyType;
        return this;
    }

    /**
     * Get currencyType
     *
     * @return currencyType
     **/
    @Schema(example = "EUR", description = "")

    public String getCurrencyType()
    {
        return currencyType;
    }

    public void setCurrencyType(String currencyType)
    {
        this.currencyType = currencyType;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return Objects.equals(this.transactionId, transaction.transactionId) &&
                Objects.equals(this.dateTimeCreated, transaction.dateTimeCreated) &&
                Objects.equals(this.senderAccount, transaction.senderAccount) &&
                Objects.equals(this.receiverAccount, transaction.receiverAccount) &&
                Objects.equals(this.amount, transaction.amount) &&
                Objects.equals(this.AMOUNT_LIMIT, transaction.AMOUNT_LIMIT) &&
                Objects.equals(this.currencyType, transaction.currencyType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(transactionId, dateTimeCreated, senderAccount, receiverAccount, amount, AMOUNT_LIMIT, currencyType);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("class Transaction {\n");

        sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
        sb.append("    dateTimeCreated: ").append(toIndentedString(dateTimeCreated)).append("\n");
        sb.append("    senderIBAN: ").append(toIndentedString(senderAccount)).append("\n");
        sb.append("    receiverIBAN: ").append(toIndentedString(receiverAccount)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    amountLimit: ").append(toIndentedString(AMOUNT_LIMIT)).append("\n");
        sb.append("    currencyType: ").append(toIndentedString(currencyType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o)
    {
        if (o == null)
        {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
