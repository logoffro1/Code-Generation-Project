package io.swagger.util;

import io.swagger.model.AuthorizedUser;
import io.swagger.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

/**
 * Custom class to check which user is logged in
 * needed for authorization of methods
 */
public class LoggedInUser extends User{

    private static AuthorizedUser user ;

    public static Boolean isEmployee() {
        if (LoggedInUser.getUserRoles().contains(User.RoleEnum.ROLE_EMPLOYEE)) {
            return true;
        }
        return false;
    }

    public static ArrayList<User.RoleEnum> getUserRoles() {

        user = (AuthorizedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<User.RoleEnum> roles = new ArrayList<>();

        user.getAuthorities().forEach(auth -> {
            roles.add(User.RoleEnum.valueOf(auth.getAuthority()));
        });
        return roles;
    }

    public static Long getUserId() {
        return (long)user.getId();
    }

    public static AuthorizedUser getUserDetails() {
        return user;
    }
}
