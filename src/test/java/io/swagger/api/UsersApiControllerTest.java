package io.swagger.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    private User user;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
         user = new User("firstName","lastName","email","password","090078601", User.RoleEnum.ROLE_EMPLOYEE);
         user.setId(1003);
    }

    //stopped working for some reason
    @Test
    public void getUsersShouldReturnJsonArray() throws Exception{
       given(userService.getAllUsers()).willReturn(List.of(user));
        this.mvc.perform(
                get("/users"))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$",hasSize(1))
                );
    }

    @Test
    public void createUser() throws Exception{

        this.mvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserShouldReturnCreated() throws Exception{

        this.mvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void CanGetAllUsers() throws Exception {

        this.mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void CanGetUserById() throws Exception {

        mvc.perform((get("/users/" + user.getId()))
        ).andExpect(status().isOk());
    }

    //also doesn't work
    @Test
    void CanDeleteUser() throws Exception {

        mvc.perform(delete("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    //doeesn't work
    @Test
    void CanUpdateUser() throws Exception {

        mvc.perform((put("/users/1003"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void CreateUserShouldNotBeNull() throws Exception{

        Exception exception = assertThrows(NullPointerException.class,
                ()-> this.mvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(null)))
                        .andExpect(status().is4xxClientError())
        );
        assertEquals("user cannot be null",exception.getMessage());
    }

}