package io.swagger.service;

import io.swagger.model.ModifyUserDTO;
import io.swagger.model.User;
import io.swagger.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers(Integer limit,Integer offset);
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    void updateUser(ModifyUserDTO user,long id);
    boolean isUserPresent(long id);
}
