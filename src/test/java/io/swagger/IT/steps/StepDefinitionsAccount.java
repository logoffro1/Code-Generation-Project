package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.*;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class StepDefinitionsAccount {
    private String accountUrl = "http://localhost:8080/api/accounts";
    private String loginUrl = "http://localhost:8080/api/login";
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;
    private RestTemplate template = new RestTemplate();
    private String token;
    private HttpEntity<String> entity;

    private String httpResponseCode;

    public void validateLogin(String email, String password) throws URISyntaxException, JsonProcessingException, JSONException {

        ObjectMapper mapper = new ObjectMapper();
        UserLogin login = new UserLogin(email, password);

        URI uri = new URI(loginUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
        token = responseEntity.getBody().substring(7);
    }

    @Given("I am Employee")
    public void iAmEmployee() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("JohnDoe@gmail.com","johnnie123");
    }

    @Given("I am Customer")
    public void iAmCustomer() throws URISyntaxException, JsonProcessingException, JSONException {
        //"JohnDoe@gmail.com", "johnnie123"
        validateLogin("willliamSmith@gmail.com", "william123");
    }


    @Given("I am User")
    public void iAmUser() throws JsonProcessingException, JSONException, URISyntaxException {
        validateLogin("JohnDoe@gmail.com", "johnnie123"); //Doesnt matter if employee or customer
    }


    @When("I want to get all acounts")
    public void iWantToGetAllAcounts() throws URISyntaxException {
        URI uri = new URI(accountUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity.getBody());
    }

    @Then("Display Http Status {int}")
    public void displayHttpStatus(int code) {
        Assert.assertEquals(code,responseEntity.getStatusCodeValue());
    }

    @When("Soft Deletes the account by entering iban {string}")
    public void softDeletesTheAccountByEnteringIban(String accountId) throws URISyntaxException {
        String deleteAccountUrl = accountUrl + "/" + accountId;
        TestRestTemplate transactionTemplate = new TestRestTemplate();
        URI uri = new URI(deleteAccountUrl);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<String>("", httpHeaders);

        responseEntity = transactionTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
    }

    @Then("I get ApiRequestException with Exception message {string}")
    public void iGetApiRequestExceptionWithExceptionMessage(String arg0) {
        System.out.println(responseEntity.getHeaders());
    }
    @Then("I see my balance {int}")
    public void iSeeMyBalance(int balance) {
    }

    
    @When("I want to create a new account for Customer with user id of {int} a balance of {int} with an absolute limit of {int} with an enum of  {string} and with a status of {string}")
    public void iWantToCreateANewAccountForCustomerWithUserIdOfABalanceOfWithAnAbsoluteLimitOfWithAnEnumOfAndWithAStatusOf(int userId, int balance, int absoluteLimit, String typeEnum, String statusEnum) throws URISyntaxException, JsonProcessingException {
        TestRestTemplate accountTemplate = new TestRestTemplate();
        URI uri = new URI(accountUrl);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + token);

        CreateAccountDTO createAccountDTO= new CreateAccountDTO(BigDecimal.valueOf(absoluteLimit),userId,Account.StatusEnum.valueOf(statusEnum),BigDecimal.valueOf(balance), Account.TypeEnum.valueOf(typeEnum));
        ObjectMapper mapper = new ObjectMapper();
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(createAccountDTO), httpHeaders);

        responseEntity = accountTemplate.postForEntity(uri, entity, String.class);

    }
    @When("I enter my Iban {string} to get my account")
    public void iEnterMyIbanToGetMyAccount(String accountId) throws URISyntaxException {
        URI uri = new URI(accountUrl + "/"+accountId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @When("Updating account with the iban {string}  to  {string}")
    public void updatingAccountWithTheIbanTo(String iban, String typeEnum) throws URISyntaxException, JsonProcessingException {
        TestRestTemplate transactionTemplate = new TestRestTemplate();
        URI uri = new URI(accountUrl + "/" + iban);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + token);

        ObjectMapper mapper = new ObjectMapper();
        ModifyAccountDTO accountDTO = new ModifyAccountDTO(Account.TypeEnum.valueOf(typeEnum));
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(accountDTO), httpHeaders);

        responseEntity = transactionTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
    }
}




