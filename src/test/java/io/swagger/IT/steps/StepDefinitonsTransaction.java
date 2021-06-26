package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.*;
import io.swagger.service.TransactionServiceImpl;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;


public class StepDefinitonsTransaction {


    private String baseUrl = "http://localhost:8080/api/transactions";
    private String loginUrl = "http://localhost:8080/api/login";
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;
    private RestTemplate template = new RestTemplate();
    private String token;
    private HttpEntity<String> entity;
    ObjectMapper mapper = new ObjectMapper();
    private String httpResponseCode;


    public StepDefinitonsTransaction() {
    }

    public void validateLogin(String email, String password) throws URISyntaxException, JsonProcessingException, JSONException {


        UserLogin login = new UserLogin(email, password);

        URI uri = new URI(loginUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
        token = responseEntity.getBody().substring(7);
    }

    @Given("I am an employee")
    public void iAmAnEmployee() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("JohnDoe@gmail.com", "johnnie123");
    }

    @When("I want to see all the transactions")
    public void iWantToSeeAllTheTransactions() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("I will see all the transactions")
    public void iWillSeeAllTheTransactions() {
        Assert.assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @When("I want to get transaction by id")
    public void iWantToGetTransactionById() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/4");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("I will get transaction by id")
    public void iWillGetTransactionById() {
        Assert.assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Given("I am an user")
    public void iAmAnUser() throws JsonProcessingException, JSONException, URISyntaxException {
        //here it's irrelevant if user is customer or employee
        validateLogin("JohnDoe@gmail.com", "johnnie123");
    }

    @When("I want to get transaction by invalid id")
    public void iWantToGetTransactionByInvalidId() throws URISyntaxException {
        URI uri = new URI(baseUrl + "/3000");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("I will get {string} exception")
    public void iWillGetTransactionWithTheSpecifiedIDNotFoundException() {
        Assert.assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Given("I am a customer")
    public void iAmACustomer() throws JsonProcessingException, JSONException, URISyntaxException {
        validateLogin("willliamSmith@gmail.com", "william123");
        // validateLogin("JohnDoe@gmail.com", "johnnie123");
    }

    @When("I want to get transaction by wrong id")
    public void iWantToGetTransactionByWrongId() {
    }

    @When("I want to delete a transaction by id")
    public void iWantToDeleteATransactionById() {
    }

    @Then("I will delete transaction by id")
    public void iWillDeleteTransactionById() {
    }

    @When("I want to delete a transaction by invalid id")
    public void iWantToDeleteATransactionByInvalidId() {
    }

    @Then("I will get a {string} exception")
    public void iWillGetAException(String arg0) {
    }

    @When("I want to create a transaction")
    public void iWantToCreateATransaction() {
    }

    @Then("I will create a new transaction")
    public void iWillCreateANewTransaction() {
    }

    @When("I want to create a transaction and sender user is null")
    public void iWantToCreateATransactionAndSenderUserIsNull() {
    }

    @When("I want to create a transaction and receiver user is null")
    public void iWantToCreateATransactionAndReceiverUserIsNull() {
    }

    @When("I want to create a transaction and transaction amount is invalid")
    public void iWantToCreateATransactionAndTransactionAmountIsInvalid() {
    }

    @When("I want to create a transaction and transaction amount is higher than user transaction limit")
    public void iWantToCreateATransactionAndTransactionAmountIsHigherThanUserTransactionLimit() {
    }

    @When("I want to create a transaction and the user's daily limit is exceeded")
    public void iWantToCreateATransactionAndTheUserSDailyLimitIsExceeded() {
    }

    @When("I want to create a transaction and the transaction amount is bigger than account balance")
    public void iWantToCreateATransactionAndTheTransactionAmountIsBiggerThanAccountBalance() {
    }

    @When("I want to create a transaction and sender account is closed")
    public void iWantToCreateATransactionAndSenderAccountIsClosed() {
    }

    @When("I want to create a transaction and accounts are of different types")
    public void iWantToCreateATransactionAndAccountsAreOfDifferentTypes() {
    }
}
