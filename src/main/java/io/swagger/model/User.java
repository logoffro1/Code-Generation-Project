package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")

@Entity
@SequenceGenerator(name = "user_seq", initialValue =  1001)
public class User
{

  @JsonProperty("id")
  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_seq")
  private long id;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("phoneNumber")
  private String phoneNumber = null;

  @JsonProperty("dayLimit")
  private Double dayLimit = null;

  @JsonProperty("currentTransactionsAmount")
  private Double currentTransactionsAmount = 0.00;

  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;

  @JsonProperty("creationDate")
  private OffsetDateTime creationDate = null;

  public User()
  {
  }

  public User(String firstName, String lastName, String email, String password, String phoneNumber, RoleEnum role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.creationDate = OffsetDateTime.now();
    this.role = role;
  }

  public User(String firstName, String lastName, String email, String password, String phoneNumber, Double dayLimit, Double transactionLimit, RoleEnum role)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.dayLimit = dayLimit;
    this.transactionLimit = transactionLimit;
    this.role = role;
    this.creationDate = OffsetDateTime.now();

  }

  public User(String firstName, String lastName, String emailAddress, String password, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.dayLimit = dayLimit;
    this.transactionLimit = transactionLimit;
    this.creationDate = OffsetDateTime.now();
  }

  /**
   * Gets or Sets role
   */
  public enum RoleEnum
  {
    EMPLOYEE("employee"),

    CUSTOMER("customer");

    private String value;

    RoleEnum(String value)
    {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString()
    {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String text)
    {
      for (RoleEnum b : RoleEnum.values())
      {
        if (String.valueOf(b.value).equals(text))
        {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("role")
  private RoleEnum role = null;

  public User id(Integer id)
  {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   **/
  @Schema(required = false)
  public long getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public User firstName(String firstName)
  {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   *
   * @return firstName
   **/
  @Schema(example = "john", required = true, description = "")
  @NotNull

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public User lastName(String lastName)
  {
    this.lastName = lastName;
    return this;
  }

  public Double getTransactionLimit()
  {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit)
  {
    this.transactionLimit = transactionLimit;
  }

  public Double getDayLimit()
  {
    return dayLimit;
  }

  public void setDayLimit(Double dayLimit)
  {
    this.dayLimit = dayLimit;
  }

  public Double getCurrentTransactionsAmount()
  {
    return currentTransactionsAmount;
  }

  public void setCurrentTransactionsAmount(Double currentTransactionsAmount)
  {
    this.currentTransactionsAmount = currentTransactionsAmount;
  }

  /**
   * Get lastName
   *
   * @return lastName
   **/
  @Schema(example = "winchester", required = true, description = "")
  @NotNull

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public User email(String email)
  {
    this.email = email;
    return this;
  }

  /**
   * Get email
   *
   * @return email
   **/
  @Schema(example = "john@gmail.com", required = true, description = "")
  @NotNull

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public User password(String password)
  {
    this.password = password;
    return this;
  }

  /**
   * Get password
   *
   * @return password
   **/
  @Schema(example = "whatever", required = true, description = "")
  @NotNull

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public User phoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   *
   * @return phoneNumber
   **/
  @Schema(example = "090078601", required = true, description = "")
  @NotNull

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  public User creationDate(OffsetDateTime creationDate)
  {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   *
   * @return creationDate
   **/
  @Schema(required = true, description = "")
  @NotNull

  @Valid
  public OffsetDateTime getCreationDate()
  {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate)
  {
    this.creationDate = creationDate;
  }

  public User role(RoleEnum role)
  {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role
   **/
  @Schema(required = true, description = "")
  @NotNull

  public RoleEnum getRole()
  {
    return role;
  }

  public void setRole(RoleEnum role)
  {
    this.role = role;
  }


  @Override
  public boolean equals(java.lang.Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
            Objects.equals(this.firstName, user.firstName) &&
            Objects.equals(this.lastName, user.lastName) &&
            Objects.equals(this.email, user.email) &&
            Objects.equals(this.password, user.password) &&
            Objects.equals(this.phoneNumber, user.phoneNumber) &&
            Objects.equals(this.creationDate, user.creationDate) &&
            Objects.equals(this.role, user.role);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(firstName, lastName, email, password, phoneNumber, creationDate, role);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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