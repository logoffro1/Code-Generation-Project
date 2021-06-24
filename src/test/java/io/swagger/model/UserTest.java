package io.swagger.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.nio.channels.AsynchronousServerSocketChannel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
public class UserTest {

    private User mockUser;

    @BeforeEach
    public void init() {
        mockUser = new User("John", "William", "johnWilliam@gmail.com", "John123", "090078601", User.RoleEnum.ROLE_EMPLOYEE);
    }

    @Test
    public void createUserShouldNotBeNull() {
        Assertions.assertNotNull(mockUser);
    }

    @Test
    public void getEmailReturnsEmail(){
        mockUser.getEmail();
    }

    @Test
    public void settingIdBelowZeroShouldThrowException(){
        Assertions.assertThrows(Exception.class,
                ()-> mockUser.setId(0));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> mockUser.setId(-1));
        assertEquals("Id should not be below zero", exception.getMessage());
    }

}

