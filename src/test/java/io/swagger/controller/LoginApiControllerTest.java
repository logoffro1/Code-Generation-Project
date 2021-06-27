package io.swagger.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void loginTestReturnsOk() throws Exception {

        UserLogin login = new UserLogin("JohnDoe@gmail.com","johnnie123");
        ObjectMapper mapper=  new ObjectMapper();
        this.mvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }
}
