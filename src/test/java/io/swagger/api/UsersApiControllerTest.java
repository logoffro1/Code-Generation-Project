package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import io.swagger.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    @Autowired
    private UserRepository userRepo;

    @MockBean
    private User user;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        user = new User("firstName","lastName","email@gmail.com","password","090078601", User.RoleEnum.ROLE_EMPLOYEE);
        user.setId(1003);
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void getUsersShouldReturnJsonArray() throws Exception{
        this.mvc.perform(
                get("/users"))
                .andExpect(
                        status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void createUserShouldReturnCreated() throws Exception{

        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void CanGetAllUsers() throws Exception {

        this.mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    //doesn't work
    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void CanGetUserById() throws Exception {

        this.mvc.perform(
                get("/users/"+ this.user.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void CanDeleteUser() throws Exception {

        this.mvc.perform(
                delete("/users/"+this.user.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void CanUpdateUser() throws Exception {

        userService.createUser(this.user);
        this.mvc.perform(
                put("/users/" + this.user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE).
                        content(mapper.writeValueAsString(this.user)))
                .andExpect(status().isOk());
    }

    //doesn't work
    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void CreateUserShouldNotBeNull() throws Exception{

        User user = null;
        Exception exception = assertThrows(ApiRequestException.class,
                ()-> this.mvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(user)))
        );
        Assertions.assertEquals("User can't be null", exception.getMessage());
    }
}