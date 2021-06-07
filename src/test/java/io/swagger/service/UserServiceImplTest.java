package io.swagger.service;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserServiceImpl userService;
    private AutoCloseable autoCloseable;


    @Autowired
    @InjectMocks
    private UserServiceImpl userService1;
    private List<User> userList;

    private User mockUser;

    @BeforeEach
    public void setUp() {

        userList = new ArrayList<>();
        mockUser = new User("John","Doe","JohnDoe@gmail.com","johnnie123","213712983", User.RoleEnum.CUSTOMER);

        userList.add(mockUser);
    }

    @AfterEach
    public void tearDown() {
        mockUser = null;
        userList = null;
    }

    @Test
    public void CanAddCreatedUser(){

        userRepository.save(mockUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> userList1 = userService.getAllUsers();
        assertEquals(userList1, userList);
    }

    @Test
    public void CanGetAllUsers(){
        userService.getAllUsers();
        verify(userRepository.findAll());
    }


}