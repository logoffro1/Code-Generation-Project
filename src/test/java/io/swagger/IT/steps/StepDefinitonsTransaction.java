package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.Login;
import io.swagger.model.UserLogin;
import io.swagger.service.TransactionServiceImpl;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
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

    @And("Transaction exists")
    public void transactionExists() {
    }

    @When("I want to get transaction by id")
    public void iWantToGetTransactionById() {
    }

    @Then("I will get transaction by id")
    public void iWillGetTransactionById() {
    }

    @Given("I am an user")
    public void iAmAnUser() {
    }

    @And("Transaction does not exist")
    public void transactionDoesNotExist() {
    }

    @Then("I will get {string} exception")
    public void iWillGetException(String arg0) {
    }

    @Given("I am a customer")
    public void iAmACustomer() {
    }

    @And("Transaction belongs to customer")
    public void transactionBelongsToCustomer() {
    }

    @And("Transaction belongs to other customer")
    public void transactionBelongsToOtherCustomer() {
    }

    @When("I want to delete a transaction by id")
    public void iWantToDeleteATransactionById() {
    }

    @Then("I will delete transaction by id")
    public void iWillDeleteTransactionById() {
    }

    @And("Transaction ID is invalid")
    public void transactionIDIsInvalid() {
    }

    @Then("I will get a {string} exception")
    public void iWillGetAException(String arg0) {
    }

    @Given("I am a user")
    public void iAmAUser() {
    }

    @When("I want to create a transaction")
    public void iWantToCreateATransaction() {
    }

    @Then("I will create a new transaction")
    public void iWillCreateANewTransaction() {
    }

    @And("Sender user is null")
    public void senderUserIsNull() {
    }

    @And("Receiver user is null")
    public void receiverUserIsNull() {
    }

    @And("Transaction amount is bigger than transaction amount limit")
    public void transactionAmountIsBiggerThanTransactionAmountLimit() {
    }

    @And("Transaction amount is higher than the user's transaction limit")
    public void transactionAmountIsHigherThanTheUserSTransactionLimit() {
    }

    @And("The user has exceeded it's daily limit")
    public void theUserHasExceededItSDailyLimit() {
    }

    @And("The transaction amount is bigger than the account's balance")
    public void theTransactionAmountIsBiggerThanTheAccountSBalance() {
    }

    @And("The sending account is closed")
    public void theSendingAccountIsClosed() {
    }

    @And("User wants to send money to another user that does not have the same account TYPE")
    public void userWantsToSendMoneyToAnotherUserThatDoesNotHaveTheSameAccountTYPE() {
    }
}
