package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.ModifyUserDTO;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserServiceImpl userServiceImpl;

    private List<User> userList;
    private User mockUser;
    private ModifyUserDTO modifyUserDTO;

    @BeforeEach
    public void setUp() {

        userList = new ArrayList<>();
        mockUser = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.ROLE_CUSTOMER);
        userServiceImpl.createUser(mockUser);
        mockUser.setId(1002);
        userList.add(mockUser);
    }

    @AfterEach
    public void tearDown() {
        mockUser = null;
        userList = null;
    }

    @Test
    public void getAllUsers(){

        userRepository.save(mockUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> userList1 = userServiceImpl.getAllUsers();
        assertEquals(userList1, userList);
        verify(userRepository).findAll();

    }

    @Test
    public void addCreatedUser(){

        userRepository.save(mockUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> userList1 = userServiceImpl.getAllUsers();
        assertEquals(userList1, userList);
    }

    @Test
    void getUserById() {

        when(userRepository.findById(this.mockUser.getId())).thenReturn(java.util.Optional.ofNullable(this.mockUser));
        User returnedUser = userServiceImpl.getUserById(this.mockUser.getId());
        assertEquals(returnedUser,this.mockUser);
    }

    //doesn't work
    @Test
    void updateUser() {

        modifyUserDTO = new ModifyUserDTO("William",
                                           "Smith",
                                            "090078601",
                                            "william@gmail.com",
                                            "william123");

        userServiceImpl.updateUser(modifyUserDTO,this.mockUser.getId());
        assertEquals(modifyUserDTO,this.mockUser);
    }

    @Test
    void gettingUserByIdThrowsExceptionIfUserDoesnotExist(){

        Exception exception = assertThrows(ApiRequestException.class,
                () -> userServiceImpl.getUserById(1005L));
        Assertions.assertEquals("User with the specified Id was not found", exception.getMessage());

    }

    @Test
    void gettingUserByIdThrowsExceptionIfIdIsNotInTheRangeOf1000(){

        Exception exception = assertThrows(ApiRequestException.class,
                () -> userServiceImpl.getUserById(1L));
        Assertions.assertEquals("Id less than 1001 doesn't exist.Try putting an id in the range of 1000", exception.getMessage());

    }

    //doesn't work
    @Test
    void creatingUserWithTheSameEmailThrowsException(){

        User mockUser2 = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.ROLE_CUSTOMER);

        Exception exception = assertThrows(ApiRequestException.class,
                () -> userServiceImpl.createUser(mockUser2));
        Assertions.assertEquals("This email is already in use.", exception.getMessage());
    }

    @Test
    void getUsersThrowsExceptionIfOffsetIsNull(){

        Exception exception = assertThrows(ApiRequestException.class,
                () -> userServiceImpl.getUsers(5,null));
        Assertions.assertEquals("Offset can't be lower than 0 or NULL.", exception.getMessage());
    }

    @Test
    void getUsersThrowsExceptionIfLimitIsNull(){

        Exception exception = assertThrows(ApiRequestException.class,
                () -> userServiceImpl.getUsers(null,0));
        Assertions.assertEquals("Limit can't be lower than 0 or NULL.", exception.getMessage());
    }

}