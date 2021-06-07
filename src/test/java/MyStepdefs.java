import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.User;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class MyStepdefs {

    @Given("the user is an employee")
    public void theUserIsAnEmployee() {
        User user = new User("John","Doe","JohnDoe@gmail.com","johnnie123","213712983", User.RoleEnum.EMPLOYEE);
    }

    @SneakyThrows
    @When("visiting the getUsers endpoint")
    public void visitingTheGetUsersEndpoint() {
        String baseUrl = "http://localhost:8080/api/users";
        HttpHeaders headers = new HttpHeaders();
        URI uri = new URI(baseUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate template = new RestTemplate();ResponseEntity<String> responseEntity = template.getForEntity(uri, String.class);
    }

    @And("the http verb is GET")
    public void theHttpVerbIsGET() {
    }

    @And("the offset and limit are set")
    public void theOffsetAndLimitAreSet() {
    }

    @Then("the employee should be able to retrieve list of all users")
    public void theEmployeeShouldBeAbleToRetrieveListOfAllUsers() {
    }

    @When("visiting the createUser endpoint")
    public void visitingTheCreateUserEndpoint() {
    }

    @And("user is given in the request body")
    public void userIsGivenInTheRequestBody() {
    }

    @Then("user should be created")
    public void userShouldBeCreated() {
    }

    @When("visiting the getUserById endpoint")
    public void visitingTheGetUserByIdEndpoint() {
    }

    @And("the user id is given in the url")
    public void theUserIdIsGivenInTheUrl() {
    }

    @Then("the employee should be able to get the user with the specified id")
    public void theEmployeeShouldBeAbleToGetTheUserWithTheSpecifiedId() {
    }

    @When("visiting the updateUser endpoint")
    public void visitingTheUpdateUserEndpoint() {
    }

    @And("the updated information is given in the request body")
    public void theUpdatedInformationIsGivenInTheRequestBody() {
    }

    @Then("the user should be updated in the database")
    public void theUserShouldBeUpdatedInTheDatabase() {
    }

    @When("visiting the deleteUser endpoint")
    public void visitingTheDeleteUserEndpoint() {
    }

    @Then("the user should be deleted from the database")
    public void theUserShouldBeDeletedFromTheDatabase() {
    }

    @Given("the user is a customer")
    public void theUserIsACustomer() {
    }

    @And("the user information belongs to the user that is logged in")
    public void theUserInformationBelongsToTheUserThatIsLoggedIn() {
    }
}
