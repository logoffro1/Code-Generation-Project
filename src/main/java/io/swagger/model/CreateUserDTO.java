package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

/**
 * DTO class for creating user
 */
@Data
public class CreateUserDTO {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("password")
    private String password;

    @JsonProperty("currentTransactionsAmount")
    private Double currentTransactionsAmount = 0.00;

    @JsonProperty("transactionLimit")
    private Double transactionLimit = null;

    @JsonProperty("role")
    private User.RoleEnum role;

    public CreateUserDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress,String password, @NonNull User.RoleEnum role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.role = role;
    }

    public CreateUserDTO() {

    }

    @Schema(example = "john", required = true, description = "")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Schema(example = "doe", required = true, description = "")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Schema(example = "090078601", required = true, description = "")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Schema(example = "johnDoe@gmail.com", required = true, description = "")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Schema(example = "whatever", required = true, description = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCurrentTransactionsAmount() {
        return currentTransactionsAmount;
    }

    public void setCurrentTransactionsAmount(Double currentTransactionsAmount) {
        this.currentTransactionsAmount = currentTransactionsAmount;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    @Schema(example = "customer", required = true, description = "")
    public User.RoleEnum getRole() {
        return role;
    }

    public void setRole(User.RoleEnum role) {
        this.role = role;
    }
}
