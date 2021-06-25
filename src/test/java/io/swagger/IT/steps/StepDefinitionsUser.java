package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.CreateUserDTO;
import io.swagger.model.User;
import io.swagger.model.UserLogin;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
public class StepDefinitionsUser {

    private String token;
    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate template = new RestTemplate();
    private String baseUrl = "http://localhost:8080/api/users";
    private HttpEntity<String> entity;
    private ResponseEntity<String> responseEntity;

    @Given("the user is an employee")
    public void theUserIsAnEmployee() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("JohnDoe@gmail.com","johnnie123");
    }

    @When("retrieving all users")
    public void retrievingAllUsers() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer " + token);

        entity = new HttpEntity<>("",headers);
        responseEntity = template.exchange(uri, HttpMethod.GET,entity,String.class);

    }

    @Then("show http status {int}")
    public void showHttpStatus(int statusCode) {
        Assert.assertEquals(statusCode, responseEntity.getStatusCodeValue());
    }

    @When("creating a new user")
    public void creatingANewUser() throws URISyntaxException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateUserDTO user = new CreateUserDTO("John","Doe","213712983","JohnDoe123@gmail.com","whatever",User.RoleEnum.ROLE_EMPLOYEE);
        URI uri = new URI(baseUrl);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
        responseEntity = template.postForEntity(uri,entity,String.class);

    }

    @When("retrieving a user with id {string}")
    public void retrievingAUserWithId(String id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "/" + id);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer " + token);

        entity = new HttpEntity<>("",headers);
        responseEntity = template.exchange(uri, HttpMethod.GET,entity,String.class);
    }

    @When("updating a user with id {int}")
    public void updatingAUserWithId(int arg0) {
    }

    @When("deleting a user with id {int}")
    public void deletingAUserWithId(int arg0) {
    }

    @When("creating a user with id {int}")
    public void creatingAUserWithId(int arg0) {
    }

    public void validateLogin(String email, String password) throws URISyntaxException, JsonProcessingException, JSONException {


        ObjectMapper mapper = new ObjectMapper();
        UserLogin login = new UserLogin(email,password);

        URI uri = new URI("http://localhost:8080/api/login");
        headers.setContentType(MediaType.APPLICATION_JSON);

        entity = new HttpEntity<>(mapper.writeValueAsString(login),headers);
        responseEntity = template.postForEntity(uri,entity,String.class);

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        token = jsonObject.getString("Token");
    }

    @Given("the user is a customer")
    public void theUserIsACustomer() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("willliamSmith@gmail.com","william123");
    }

    @When("updating a user with id {string}")
    public void updatingAUserWithId(String id) {

    }

    @Then("the phone number should be {string}")
    public void thePhoneNumberShouldBe(String phoneNumber) throws JSONException {
        String response = responseEntity.getBody();
        JSONObject user = new JSONObject(response);
        Assert.assertEquals(phoneNumber,user.get("phoneNumber"));
    }
}
