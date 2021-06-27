package io.swagger.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * DTO class for creating user
 */
@Getter@Setter
public class UserDTO {

    @JsonProperty("firstName")
    @NotEmpty
    private String firstName;

    @JsonProperty("lastName")
    @NotEmpty
    private String lastName;

    @JsonProperty("phoneNumber")
    @NotEmpty
    private String phoneNumber;

    @JsonProperty("emailAddress")
    @NotEmpty
    private String emailAddress;

    @JsonProperty("currentTransactionsAmount")
    private Double currentTransactionsAmount = 0.00;

    @JsonProperty("transactionLimit")
    private Double transactionLimit = null;

    @JsonProperty("roles")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = User.RoleEnum.class)
    private Collection<User.RoleEnum> roles;

    public UserDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull Collection<User.RoleEnum> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public Collection<User.RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Collection<User.RoleEnum> roles) {
        this.roles = roles;
    }
}
