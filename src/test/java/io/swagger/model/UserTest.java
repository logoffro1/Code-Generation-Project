package io.swagger.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTest {

    private User mockUser;

    @BeforeEach
    public void init() {
        mockUser = new User("John", "William", "johnWilliam@gmail.com", "John123", "090078601", User.RoleEnum.EMPLOYEE);
    }

    @Test
    public void createUserShouldNotBeNull() {
        Assertions.assertNotNull(mockUser);
    }

    @Test
    public void getEmailReturnsEmail(){
        mockUser.getEmail();
    }
}

