package io.swagger.model;

import lombok.NonNull;
import org.springframework.data.annotation.Id;

import javax.management.relation.Role;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;

public class ModifyUserDTO {

    @Id
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = User.RoleEnum.class)
    private Collection<User.RoleEnum> roles;

    public ModifyUserDTO(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull String password, @NonNull Collection<User.RoleEnum> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
    }
}
