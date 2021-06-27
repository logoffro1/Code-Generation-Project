package io.swagger.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.User;
import lombok.NonNull;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

public class ResponseUserDTO {
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

    @JsonProperty("role")
    private User.RoleEnum role;

    public ResponseUserDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull User.RoleEnum role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.role = role;
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

    public User.RoleEnum  getRole() {
        return role;
    }

    public void setRole(User.RoleEnum role) {
        this.role = role;
    }
}
