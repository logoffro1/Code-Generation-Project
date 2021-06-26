Feature: Users

  Scenario: Get All Users (For employee)
    Given the user is an employee
    When visiting the getUsers endpoint
    And the http verb is GET
    And the offset and limit are set
    Then the employee should be able to retrieve list of all users

  Scenario: Create a User
    Given the user is an employee
    When visiting the createUser endpoint
    And user is given in the request body
    Then user should be created

  Scenario: Get User by ID (For employee)
    Given the user is an employee
    When visiting the getUserById endpoint
    And the user id is given in the url
    Then the employee should be able to get the user with the specified id

  Scenario: Update User (For employee)
    Given the user is an employee
    When visiting the updateUser endpoint
    And the user id is given in the url
    And the updated information is given in the request body
    Then the user should be updated in the database

  Scenario: Delete User (For employee)
    Given the user is an employee
    When visiting the deleteUser endpoint
    And the user id is given in the url
    Then the user should be deleted from the database

  Scenario: Update User (For customer)
    Given the user is a customer
    When visiting the updateUser endpoint
    And the user id is given in the url
    And the user information belongs to the user that is logged in
    And the updated information is given in the request body
    Then the user should be updated in the database