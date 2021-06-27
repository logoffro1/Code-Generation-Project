package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.dtos.ModifyUserDTO;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.util.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service Implementation class
 * Implements the interface User Service
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers(Integer limit,Integer offset) {

        if (offset == null || offset < 0)
            offset = 0; //default 0

        if (limit == null || limit < 0)
            limit = 20; //default 20

        Pageable pageable= PageRequest.of(offset,limit);
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {

        // null user shouldn't be created
        if(user == null)
            throw new ApiRequestException("User can't be null",HttpStatus.BAD_REQUEST);

        // A user with same email address shouldn't be created because it's used as a username(two users can't have the same username)
        List<User> users = getAllUsers();
        for (User u : users) {
            if(u.getEmail().equals(user.getEmail())){
                throw new ApiRequestException("This email is already in use.",HttpStatus.BAD_REQUEST);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id)  {

        if(!LoggedInUser.isEmployee())
        {
            if (!LoggedInUser.getUserId().equals(id))
            {
                throw new ApiRequestException("You are not allowed to get this user details",HttpStatus.UNAUTHORIZED);
            }
        }
        if(id < 1001)
            throw new ApiRequestException("Id less than 1001 doesn't exist.Try putting an id in the range of 1000",HttpStatus.BAD_REQUEST);

        return userRepository.findById(id).orElseThrow(() -> new ApiRequestException("User with the specified Id was not found",HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUserById(long id) {

        User user = getUserById(id);

        if(user.getStatus() == User.StatusEnum.INACTIVE)
            throw new ApiRequestException("User is inactive",HttpStatus.BAD_REQUEST);

        //Putting user to inactive state instead of deleting all the records(including its accounts and the transactions made)
        user.setStatus(User.StatusEnum.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public void updateUser(ModifyUserDTO modifyUser, long id) {

        User user = getUserById(id);

        if (modifyUser.equals(user)){
            throw new ApiRequestException("Nothing was updated",HttpStatus.NOT_MODIFIED);
        }

        //user shoulnot be able to modify the email address because its used as a username
        if(!modifyUser.getEmailAddress().equals(user.getEmail()))
        {
            throw new ApiRequestException("Email cannot be modified",HttpStatus.BAD_REQUEST);
        }

        if (modifyUser.getFirstName() != null && !modifyUser.getFirstName().isEmpty()) {
            user.setFirstName(modifyUser.getFirstName());
        }
        if (modifyUser.getLastName() != null && !modifyUser.getLastName().isEmpty()) {
            user.setLastName(modifyUser.getLastName());
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