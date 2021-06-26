package io.swagger.IT.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.Account;
import io.swagger.model.Login;
import io.swagger.model.ModifyAccountDTO;
import io.swagger.model.UserLogin;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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
        validateLogin("JohnDoe@gmail.com", "johnnie123");
//        loginWithCredentials("JohnDoe@gmail.com","johnnie123");
    }

    @When("Updating account From TypeEnum.CURRENT to  TypeEnum.SAVINGS")
    public void updatingAccountFromTypeEnumCURRENTToTypeEnumSAVINGS() throws URISyntaxException {
        URI uriForUpdate = new URI(this.accountUrl + "/NL04INHO0836583990");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.token);
        ModifyAccountDTO accountDTO = new ModifyAccountDTO(Account.TypeEnum.SAVINGS);
        HttpEntity<ModifyAccountDTO> entity = new HttpEntity<>(headers);
        responseEntity = template.exchange(uriForUpdate, HttpMethod.PUT, entity, String.class);
        httpResponseCode = responseEntity.getStatusCode().toString();
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
    public void softDeletesTheAccountByEnteringIban(String arg0) {
    }

    @When("I want to create a new account for Customer")
    public void iWantToCreateANewAccountForCustomer() {
    }

    @When("I enter my Iban {string} to get my account to see my balance \\(Using get account by iban endpoint)")
    public void iEnterMyIbanToGetMyAccountToSeeMyBalanceUsingGetAccountByIbanEndpoint(String arg0) {
    }

    @Then("I see my balance {int}")
    public void iSeeMyBalance(int arg0) {
    }

    @When("I try to get my account by Iban {string}")
    public void iTryToGetMyAccountByIban(String arg0) {
    }

    @When("I try to create account with a balance of {int} lower than absolutelimit {int}")
    public void iTryToCreateAccountWithABalanceOfLowerThanAbsolutelimit(int arg0, int arg1) {
    }

    @Then("I get ApiRequestException with Exception message {string}")
    public void iGetApiRequestExceptionWithExceptionMessage(String arg0) {
    }

    @When("I try to get an account by iban")
    public void iTryToGetAnAccountByIban() {
    }

    @When("I try to create an account with a null user")
    public void iTryToCreateAnAccountWithANullUser() {
    }

    @Then("I get ApiRequestException with Exception message \"User can not be null.")
    public void iGetApiRequestExceptionWithExceptionMessageUserCanNotBeNull() throws Throwable {    // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();}
    }

    @Then("Display ApiRequestException {string}")
    public void displayApiRequestException(String arg0) {
    }

    @When("I try to get an account by IBAN that does not belong to my account")
    public void iTryToGetAnAccountByIBANThatDoesNotBelongToMyAccount() {
    }

    @Then("I get HttpStatus {int}")
    public void iGetHttpStatus(int arg0) {
    }

    @When("I try to get all accounts with the limit of {int} and offset of {int}")
    public void iTryToGetAllAccountsWithTheLimitOfAndOffsetOf(int arg0, int arg1) {
    }

    @Then("I receive the first {int} accounts from database")
    public void iReceiveTheFirstAccountsFromDatabase(int arg0) {
    }

    @When("I try to get an account by iban with an iban that does not exist in the database")
    public void iTryToGetAnAccountByIbanWithAnIbanThatDoesNotExistInTheDatabase() {
    }

    @When("I try to update Banks account to change type from current to savings")
    public void iTryToUpdateBanksAccountToChangeTypeFromCurrentToSavings() {
    }

    @Then("I get ApiRequesException with Exceptionmessage {string}")
    public void iGetApiRequesExceptionWithExceptionmessage(String arg0) {
    }
}




