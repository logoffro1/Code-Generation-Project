package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.*;
import io.swagger.model.dtos.CreateTransactionDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


public class StepDefinitonsTransaction {


    private String baseUrl = "http://localhost:8080/api/transactions";
    private String loginUrl = "http://localhost:8080/api/login";
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;
    private RestTemplate template = new RestTemplate();
    private String token;
    private HttpEntity<String> entity;
    ObjectMapper mapper = new ObjectMapper();


    public StepDefinitonsTransaction() {
    }

    public void validateLogin(String email, String password) throws URISyntaxException, JsonProcessingException, JSONException {


        UserLogin login = new UserLogin(email, password);

        URI uri = new URI(loginUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        token = jsonObject.getString("Token");
    }

    @Given("I am an employee")
    public void iAmAnEmployee() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("JohnDoe@gmail.com", "johnnie123");
    }

    @When("I want to see all the transactions")
    public void iWantToSeeAllTheTransactions() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBearerAuth(token);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }


    @When("I enter id {int} to get transaction")
    public void iEnterIdToGetTransaction(int id) throws URISyntaxException {

        URI uri = new URI(baseUrl + "/" + id);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("Show http status {int}")
    public void showHttpStatus(int httpCode) {
        Assert.assertEquals(httpCode, responseEntity.getStatusCodeValue());
    }

    @Given("I am an user")
    public void iAmAnUser() throws JsonProcessingException, JSONException, URISyntaxException {
        //it's irrelevant if user is customer or employee
        validateLogin("JohnDoe@gmail.com", "johnnie123");
    }

    @Given("I am a customer")
    public void iAmACustomer() throws JsonProcessingException, JSONException, URISyntaxException {
        validateLogin("willliamSmith@gmail.com", "william123");
    }

    @When("I enter id {int} to delete transaction")
    public void iEnterIdToDeleteTransaction(int id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "/" + id);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.DELETE, entity, String.class);
    }

    @When("I want to create a transaction with senderIBAN {string} and receiverIBAN {string} and amount {double} and currencyType {string}")
    public void iWantToCreateATransactionWithSenderIBANAndReceiverIBANAndAmountAndCurrencyType(String senderIBAN, String receiverIBAN, double amount, String currencyType) throws URISyntaxException, JsonProcessingException {

        CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO(senderIBAN, receiverIBAN, amount, currencyType);
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(mapper.writeValueAsString(createTransactionDTO), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }
}
