package io.swagger.service;


import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public void deleteUserById(long id) {

        //IMP: delete transaction and account before deleting the user
    }

    @Override
    public void updateUser(User user) {

    }
}