package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class UserLogin   {
    @Schema(required = true, description = "")
    @NotNull
    @JsonProperty("emailAddress")
    private String emailAddress = null;

    @Schema(required = true, description = "")
    @NotNull
    @JsonProperty("password")
    private String password = null;

    @JsonProperty("roles")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = User.RoleEnum.class)
    private Collection<User.RoleEnum> roles;

    public UserLogin(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<User.RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Collection<User.RoleEnum> roles) {
        this.roles = roles;
    }
}
