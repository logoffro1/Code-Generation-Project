package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
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

}
