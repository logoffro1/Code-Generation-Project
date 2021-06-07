package io.swagger.service;


import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers(Integer limit,Integer offset) {

        if (offset == null || offset < 0)
            throw new ApiRequestException("Offset can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        if (limit == null || limit < 0)
            throw new ApiRequestException("Limit can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        Pageable pageable= PageRequest.of(offset,limit);
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id)  {
        if(id < 1001)
            throw new ApiRequestException("Id less than 1001 doesn't exist.Try putting an id in the range of 1000",HttpStatus.BAD_REQUEST);

        return userRepository.findById(id).orElseThrow(() -> new ApiRequestException("User with the specified Id was not found",HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUserById(long id) {

        //TO DO: delete the transactions and account too
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User modifyUser, long id) {

        User user = getUserById(id);

        if (modifyUser.equals(user)){
            throw new ApiRequestException("Nothing was updated",HttpStatus.NOT_MODIFIED);
        }

        if (modifyUser.getFirstName() != null && !modifyUser.getFirstName().isEmpty()) {
            user.setFirstName(modifyUser.getFirstName());
        }
        if (modifyUser.getLastName() != null && !modifyUser.getLastName().isEmpty()) {
            user.setLastName(modifyUser.getLastName());
        }
        if (modifyUser.getEmail() != null && !modifyUser.getEmail().isEmpty()) {
            user.setEmail(modifyUser.getEmail());
        }
        if (modifyUser.getPassword() != null && !modifyUser.getPassword().isEmpty()) {
            user.setPassword(modifyUser.getPassword());
        }
        if (modifyUser.getPhoneNumber() != null && !modifyUser.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(modifyUser.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public boolean isUserPresent(long id) {
        return userRepository.existsById(id);
    }
}