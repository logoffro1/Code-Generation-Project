package io.swagger.service;

import io.swagger.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User createUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
}
