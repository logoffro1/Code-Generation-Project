package io.swagger.IT.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionsAccount {

    @Given("I am customer")
    public void i_am_customer() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("I want to open a new account")
    public void i_want_to_open_a_new_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("I should be able to open a new account")
    public void i_should_be_able_to_open_a_new_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("Im User")
    public void imUser() {
    }

    @When("I want to get my balance to see")
    public void iWantToGetMyBalanceToSee() {
    }

    @Then("I should be able to see my balance")
    public void iShouldBeAbleToSeeMyBalance() {
    }

    @Given("I am Employee")
    public void iAmEmployee() {
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
