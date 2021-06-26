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
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class StepDefinitionsAccount {
    private String baseUrl = "http://localhost:8080/api/Accounts";
    private String loginUrl= "http://localhost:8080/api/Login";
    private HttpHeaders headers = new HttpHeaders();
    private ResponseEntity<String> responseEntity;
    private RestTemplate template = new RestTemplate();
    private String token;
    private HttpEntity<String> entity;

    private String httpResponseCode;

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

    public void loginWithCredentials(String email,String password)throws URISyntaxException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);

        URI userUri = new URI(loginUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(login), headers);

        responseEntity = template.postForEntity(userUri, entity, String.class);
        token = responseEntity.getBody();
    }


    @Given("I am Employee")
    public void iAmEmployee() throws URISyntaxException, JsonProcessingException, JSONException {
        validateLogin("JohnDoe@gmail.com","johnnie123");
//        loginWithCredentials("JohnDoe@gmail.com","johnnie123");
    }

    @When("Updating account From TypeEnum.CURRENT to  TypeEnum.SAVINGS")
    public void updatingAccountFromTypeEnumCURRENTToTypeEnumSAVINGS() throws URISyntaxException {
        URI uriForUpdate =new URI(this.baseUrl+"/NL04INHO0836583990");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.token);
        ModifyAccountDTO accountDTO= new ModifyAccountDTO(Account.TypeEnum.SAVINGS);
        HttpEntity<ModifyAccountDTO>entity=new HttpEntity<>(headers);
        responseEntity=template.exchange(uriForUpdate, HttpMethod.PUT,entity,String.class);
        httpResponseCode = responseEntity.getStatusCode().toString();
    }

    @Then("Display Http Status {int}")
    public void displayHttpStatus(int arg0) {
        Assert.assertEquals(httpResponseCode,"200 OK");
    }

    @When("Soft Deletes the account by entering iban {string}")
    public void softDeletesTheAccountByEnteringIban(String arg0) {
    }

    @When("I want to create a new account for Customer")
    public void iWantToCreateANewAccountForCustomer() {
    }

    //Which means it can be either customer or employee, both is acceptable.
    @Given("I am User")
    public void iAmUser() {
    }

    @When("I want to get my balance to see")
    public void iWantToGetMyBalanceToSee() {
    }

    @Given("I am Customer")
    public void iAmCustomer() throws URISyntaxException, JsonProcessingException {
        //"JohnDoe@gmail.com", "johnnie123"
        loginWithCredentials("willliamSmith@gmail.com","william123");


    }

    @When("I try to get my account by Iban {string}")
    public void iTryToGetMyAccountByIban(String arg0) {
    }

    @When("I try to create account with a balance of {int} lower than absolutelimit {int}")
    public void iTryToCreateAccountWithABalanceOfLowerThanAbsolutelimit(int balance, int absolutelimit) {
    }

    @Then("I get {string}")
    public void iGet(String arg0) {
    }

    @Then("I get Exception message {string}")
    public void iGetExceptionMessage(String arg0) {
    }

    @Then("I get HttpStatus {int}")
    public void iGetHttpStatus(int arg0) {
    }

    @When("I try to get an account by IBAN that does not belong to my account")
    public void iTryToGetAnAccountByIBANThatDoesNotBelongToMyAccount() {
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

    @And("Iban is valid")
    public void ibanIsValid() {
    }

    @And("Iban is not present in database")
    public void ibanIsNotPresentInDatabase() {
    }

    @When("I enter my Iban {string} to get my account to see my balance")
    public void iEnterMyIbanToGetMyAccountToSeeMyBalance(String arg0) {
    }

    @Then("I see my balance {int}")
    public void iSeeMyBalance(int arg0) {
    }

    /*private Account account;
    private Double actualBalance;
    private Double expectedBalance;

    private String expectedResult;

    RestTemplate template = new RestTemplate();
    String baseUrl = "http://localhost:8080/api/Accounts";
    HttpHeaders headers = new HttpHeaders();

    ResponseEntity<String> responseEntity;

    @Given("I have an account")
    public void iHaveAnAccount() throws Exception
    {
        account = new Account("testIban", AccountType.CURRENT, 400D);
        expectedBalance = 400D;

        ObjectMapper mapper = new ObjectMapper();
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(account), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }

    @When("I want to learn about my account's balance")
    public void iWantToLearnAboutMyAccountSBalance() throws IOException, URISyntaxException
    {
        URI uri = new URI(baseUrl + "/testIban/Balance");
        headers.setContentType(MediaType.APPLICATION_JSON);

        responseEntity = template.getForEntity(uri, String.class);
        actualBalance = Double.valueOf(responseEntity.getBody());
    }

    @Then("I should be able to see my account's balance")
    public void iShouldBeAbleToSeeMyAccountSBalance()
    {
        Assert.assertEquals(actualBalance, expectedBalance);
    }
@Given("I am already an existing customer with a saving account that is with zero balance")
    public void iAmAlreadyAnExistingCustomerWithASavingAccountThatIsWithZeroBalance() throws JsonProcessingException, URISyntaxException
    {
        account = new Account("testIban", AccountType.SAVING, 0D);

        ObjectMapper mapper = new ObjectMapper();
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(account), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }

    @When("I want to close my account")
    public void iWantToCloseMyAccount() throws URISyntaxException
    {
        URI uri = new URI(baseUrl + "/testIban/Status/closed");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
        expectedResult = responseEntity.getStatusCode().toString();
    }

    @Then("I should be able to delete my account")
    public void iShouldBeAbleToDeleteMyAccount()
    {
        Assert.assertEquals("200 OK", expectedResult);
    }*/
}
