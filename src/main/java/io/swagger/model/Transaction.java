package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-24T23:22:40.244Z[GMT]")


public class Transaction   {
  @JsonProperty("transactionId")
  private Integer transactionId = null;

  @JsonProperty("dateTimeCreated")
  private OffsetDateTime dateTimeCreated = null;

  @JsonProperty("senderIBAN")
  private String senderIBAN = null;

  @JsonProperty("receiverIBAN")
  private String receiverIBAN = null;

  @JsonProperty("senderUserId")
  private Integer senderUserId = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("currencyType")
  private String currencyType = null;

  public Transaction transactionId(Integer transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Get transactionId
   * @return transactionId
   **/
  @Schema(example = "2012", description = "")
  
    public Integer getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Integer transactionId) {
    this.transactionId = transactionId;
  }

  public Transaction dateTimeCreated(OffsetDateTime dateTimeCreated) {
    this.dateTimeCreated = dateTimeCreated;
    return this;
  }

  /**
   * the date and time the transaction was created
   * @return dateTimeCreated
   **/
  @Schema(description = "the date and time the transaction was created")
  
    @Valid
    public OffsetDateTime getDateTimeCreated() {
    return dateTimeCreated;
  }

  public void setDateTimeCreated(OffsetDateTime dateTimeCreated) {
    this.dateTimeCreated = dateTimeCreated;
  }

  public Transaction senderIBAN(String senderIBAN) {
    this.senderIBAN = senderIBAN;
    return this;
  }

  /**
   * Get senderIBAN
   * @return senderIBAN
   **/
  @Schema(example = "NL23RABO2298608059", description = "")
  
    public String getSenderIBAN() {
    return senderIBAN;
  }

  public void setSenderIBAN(String senderIBAN) {
    this.senderIBAN = senderIBAN;
  }

  public Transaction receiverIBAN(String receiverIBAN) {
    this.receiverIBAN = receiverIBAN;
    return this;
  }

  /**
   * Get receiverIBAN
   * @return receiverIBAN
   **/
  @Schema(example = "NL67ABNA8265634552", description = "")
  
    public String getReceiverIBAN() {
    return receiverIBAN;
  }

  public void setReceiverIBAN(String receiverIBAN) {
    this.receiverIBAN = receiverIBAN;
  }

  public Transaction senderUserId(Integer senderUserId) {
    this.senderUserId = senderUserId;
    return this;
  }

  /**
   * Get senderUserId
   * @return senderUserId
   **/
  @Schema(example = "20939", description = "")
  
    public Integer getSenderUserId() {
    return senderUserId;
  }

  public void setSenderUserId(Integer senderUserId) {
    this.senderUserId = senderUserId;
  }

  public Transaction amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "4800", description = "")
  
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Transaction currencyType(String currencyType) {
    this.currencyType = currencyType;
    return this;
  }

  /**
   * Get currencyType
   * @return currencyType
   **/
  @Schema(example = "EUR", description = "")
  
    public String getCurrencyType() {
    return currencyType;
  }

  public void setCurrencyType(String currencyType) {
    this.currencyType = currencyType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.transactionId, transaction.transactionId) &&
        Objects.equals(this.dateTimeCreated, transaction.dateTimeCreated) &&
        Objects.equals(this.senderIBAN, transaction.senderIBAN) &&
        Objects.equals(this.receiverIBAN, transaction.receiverIBAN) &&
        Objects.equals(this.senderUserId, transaction.senderUserId) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.currencyType, transaction.currencyType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, dateTimeCreated, senderIBAN, receiverIBAN, senderUserId, amount, currencyType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    dateTimeCreated: ").append(toIndentedString(dateTimeCreated)).append("\n");
    sb.append("    senderIBAN: ").append(toIndentedString(senderIBAN)).append("\n");
    sb.append("    receiverIBAN: ").append(toIndentedString(receiverIBAN)).append("\n");
    sb.append("    senderUserId: ").append(toIndentedString(senderUserId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currencyType: ").append(toIndentedString(currencyType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
