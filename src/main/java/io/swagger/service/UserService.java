package io.swagger.service;

import io.swagger.model.ModifyUserDTO;
import io.swagger.model.User;

import java.util.List;

/**
 * User Service Interface class
 */
public interface UserService {
    List<User> getUsers(Integer limit,Integer offset);
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    void updateUser(ModifyUserDTO user,long id);
    boolean isUserPresent(long id);
}
