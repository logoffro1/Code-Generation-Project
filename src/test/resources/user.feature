Feature: Testing Users

  Scenario: Creating a user returns http status created
    Given the user is an employee
    When creating a new user
    Then show http status 201

  Scenario: Retrieving a user with Id returns http status ok
    Given the user is an employee
    When retrieving a user with id "1001"
    Then show http status 200

  Scenario: Deleting a user returns http status ok
    Given the user is an employee
    When deleting a user with id "1001"
    Then show http status 200

  Scenario: Getting one User and see the phone number
    Given  the user is an employee
    When retrieving a user with id "1001"
    Then the phone number should be "213712983"

  Scenario: Logging in provides with token
    Given the user provides "JohnDoe@gmail.com" and "johnnie123"
    Then the web token is returned

  Scenario: Getting one User and see the email address
    Given  the user is an employee
    When retrieving a user with id "1001"
    Then the emailAddress should be "JohnDoe@gmail.com"

  Scenario: Getting one User by Id and see the full name
    Given  the user is an employee
    When retrieving a user with id "1001"
    Then the full name should be "John Doe"

  Scenario: Creating null user throws exception
    Given  the user is an employee
    When creating a null user
    Then show http status 400

  Scenario: Creating a user with existing email throws exception
    Given  the user is an employee
    When creating a user with existing email
    Then show http status 502



