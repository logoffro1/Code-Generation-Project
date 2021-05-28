package io.swagger.service;


import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

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
    public User getUserById(long id) {
        User user = new User();
        try {
              user = userRepository.findById((int)id).orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return null;
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteUserById(long id) {

        //IMP: delete transaction and account before deleting the user
    }

    @Override
    public void updateUser(User user) {

    }
}