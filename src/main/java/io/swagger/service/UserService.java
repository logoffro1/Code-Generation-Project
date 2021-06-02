package io.swagger.service;

import io.swagger.model.ModifyUserDTO;
import io.swagger.model.User;
import io.swagger.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();
    User createUser(User user);
    User getUserById(long id) throws Exception;
    void deleteUserById(long id);
    void updateUser(ModifyUserDTO user);
}
