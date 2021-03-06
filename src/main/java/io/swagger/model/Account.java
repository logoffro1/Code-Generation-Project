package io.swagger.model;

import java.security.Timestamp;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.exceptions.ApiRequestException;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")

@Entity
public class Account   {

  //This is the main class that is going to be in the database.

  @Id
  @GeneratedValue
  private long accountId;

  @JsonProperty("IBAN")
  private String IBAN = null;

  @Column(precision=10, scale=2)
  @JsonProperty("absoluteLimit")
  private BigDecimal absoluteLimit = null;

  @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
  @JoinColumn(name = "userID")
  private User user = null;

  public Account(){

  }

  public Account(String IBAN,BigDecimal absoluteLimit, User user, TypeEnum type, StatusEnum status, BigDecimal balance) {
    this.absoluteLimit = absoluteLimit;
    this.IBAN = IBAN;
    this.user = user;
    this.type = type;
    this.status = status;
    this.balance = balance;
  }

  public BigDecimal getAbsoluteLimit() {
    return absoluteLimit;
  }

  public void setAbsoluteLimit(BigDecimal absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
  }

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    CURRENT("current"),
    
    SAVINGS("savings");

    private String value;

    TypeEnum(String value) {
      if(value=="current" || value=="savings")
        this.value = value;
      else{
        throw new ApiRequestException("Please enter a valid Account Type (current/savings)", HttpStatus.BAD_REQUEST);
      }
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    ACTIVE("active"),
    
    CLOSED("closed");

    private String value;

    StatusEnum(String value) {

      if(value=="active" || value=="closed")
        this.value = value;
      else{
        throw new ApiRequestException("Please enter a valid Account Status (active/closed)", HttpStatus.BAD_REQUEST);
      }
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  @Column(precision=10, scale=2)
  @JsonProperty("balance")
  private BigDecimal balance = null;


  public Account IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public Account user(User user) {
    this.user = user;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Account type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(required = true, description = "")
      @NotNull

    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Account status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(required = true, description = "")
      @NotNull

    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Account balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    if(balance.compareTo(this.absoluteLimit)==-1)
    throw new IllegalArgumentException("Balance can not be lower than absolute limit");

    this.balance = balance;
  }



  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.IBAN, account.IBAN) &&
        Objects.equals(this.user, account.user) &&
        Objects.equals(this.type, account.type) &&
        Objects.equals(this.status, account.status) &&
        Objects.equals(this.balance, account.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(IBAN, user, type, status, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    userId: ").append(toIndentedString(user)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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
