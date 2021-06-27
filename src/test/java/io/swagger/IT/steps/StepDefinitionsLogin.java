package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDTO;
import io.swagger.model.UserLogin;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class StepDefinitionsLogin {

    private String loginUrl = "http://localhost:8080/api/login";
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;
    private RestTemplate template = new RestTemplate();
    private String token;
    private HttpEntity<String> entity;

    //For login post
    @When("User tries to enter {string} as email and {string} as password")
    public void userTriesToEnterAsEmailAndAsPassword(String email, String password) throws JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        UserLogin login = new UserLogin(email, password);

        URI uri = new URI(loginUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
        token = responseEntity.getBody().substring(7);
    }

    //For comparing http status
    @Then("Http Status {int} is displayed")
    public void httpStatusIsDisplayed(int code) {
        {
            Assert.assertEquals(code,responseEntity.getStatusCodeValue());
        }
    }
}
