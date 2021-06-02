package io.swagger.model;

import lombok.NonNull;

import javax.management.relation.Role;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

public class UserDTO {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String emailAddress;
    @NotEmpty
    private String password;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = User.RoleEnum.class)
    private Collection<User.RoleEnum> roles;

    public UserDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull String password, @NonNull Collection<User.RoleEnum> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
    }
}
